/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.oss.minio.strategy;

import com.github.hiwepy.validation.MimeTypeDetectorHolder;
import hitool.core.io.FilenameUtils;
import hitool.core.io.IOUtils;
import hitool.core.lang3.time.DateUtils;
import io.hiwepy.boot.api.exception.BizRuntimeException;
import io.hiwepy.cloud.oss.core.bo.*;
import io.hiwepy.cloud.oss.core.enums.OssChannel;
import io.hiwepy.cloud.oss.core.strategy.AbstractFileStoreStrategy;
import io.hiwepy.cloud.oss.core.utils.OssUtils;
import io.hiwepy.cloud.oss.core.vo.FileInfoVO;
import io.minio.*;
import io.minio.http.Method;
import io.minio.spring.boot.MinioProperties;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/*
 * https://blog.csdn.net/justlpf/article/details/87857254
 */
@Component
@Slf4j
public class MinioFileStoreStrategy extends AbstractFileStoreStrategy {

    @Autowired
    private MinioClient minioClient;
    @Autowired
    private MinioProperties minioProperties;

    @Value("${minio.exposed-url:}")
    private String exposedUrl;

    @Override
    public OssChannel getChannel() {
        return OssChannel.OSS_MINIO;
    }

    @Override
    public FileStoreConfig getConfig() {
        FileStoreConfig config = new FileStoreConfig();
        config.setEndpoint(minioProperties.getEndpoint());
        return config;
    }


    @Override
    protected FileInfoVO handleFileUpload(FileStoreBO uploadBo, MultipartFile file, int index) throws IOException {
        try (InputStream inputStream = file.getInputStream()) {

            // 检查存储桶是否已经存在
            String bucketName = StringUtils.defaultIfBlank(uploadBo.getBucketName(), getOssProperties().getCloud().getBucket());
            // Make bucket if not exist.
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                // Make a new bucket.
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                log.info("create a new bucket called {}.", bucketName);
            } else {
                log.info("Bucket {} already exists.", bucketName);
            }
            long iFileSize = file.getSize();//文件大小
            String fullFileExtions = FilenameUtils.getFullExtension(file.getOriginalFilename());
            String fileExtions = fullFileExtions.substring(1);
            // 文件存储结果
            String uuid = getSequence().nextId().toString();
            if (!StringUtils.isEmpty(uploadBo.getFileId())) {
                uuid = uploadBo.getFileId();
            }
            String fileNameWithOutExtions = uuid;//文件名称不包括文件文件扩展名
            if (!StringUtils.isEmpty(uploadBo.getStoreFileName())) {
                fileNameWithOutExtions = FilenameUtils.removeExtension(uploadBo.getStoreFileName());
            }
            String basename = DateUtils.getDate() + File.separator + fileNameWithOutExtions;
            String objectName = basename + fullFileExtions;

            Map<String, String> headerMap = new HashMap<>();

            //String contentType = FilemimeUtils.getFileMimeType(file.getOriginalFilename());
            String contentType = MimeTypeDetectorHolder.instance().getDetector().detectMimeType(file.getOriginalFilename(), file.getInputStream());
            // 上传原文件
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(inputStream, iFileSize, minioProperties.getPartSize())
                    .contentType(contentType)
                    .headers(headerMap)
                    .build());

            String storeUrl = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().bucket(bucketName).object(objectName).method(Method.GET).build());

            // 上传的是图片且可生成缩略图的图片
            String thumbPath = null;
            String thumbUrl = null;
            if (uploadBo.isThumb() && OssUtils.isImageByExtension(fileExtions) && OssUtils.thumbableByExtension(fileExtions, uploadBo.getThumbWidth(), uploadBo.getThumbHeight())) {
                // 保存缩略图
                String thumbObjectName = basename + new ThumbImageBO(uploadBo.getThumbWidth(), uploadBo.getThumbHeight()).getPrefixName() + fileExtions;
                try (ByteArrayOutputStream output = new ByteArrayOutputStream();) {
                    Thumbnails.of(file.getInputStream()).scale(uploadBo.getThumbWidth(), uploadBo.getThumbHeight()).toOutputStream(output);
                    try (InputStream input = output.toInputStream();) {
                        minioClient.putObject(PutObjectArgs.builder()
                                .bucket(bucketName)
                                .object(thumbObjectName)
                                .stream(input, input.available(), minioProperties.getPartSize())
                                .contentType(contentType)
                                .headers(headerMap)
                                .build());
                        thumbPath = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().bucket(bucketName).object(thumbObjectName).method(Method.GET).build());
                    }
                }
            }

            // 文件存储信息
            FileInfoVO attDTO = new FileInfoVO();
            attDTO.setBucket(bucketName);
            attDTO.setUuid(uuid);
            attDTO.setName(file.getOriginalFilename());
            attDTO.setPath(objectName);
            attDTO.setUrl(storeUrl);
            if (StringUtils.isNotBlank(exposedUrl)) {
                attDTO.setUrl(this.exposeUrl(storeUrl));
            }
            if (StringUtils.isNotBlank(thumbPath)) {
                attDTO.setThumb(thumbPath);
                attDTO.setThumbUrl(thumbUrl);
                if (StringUtils.isNotBlank(exposedUrl)) {
                    attDTO.setThumbUrl(this.exposeUrl(thumbUrl));
                }
            }
            attDTO.setSize(iFileSize);
            attDTO.setIndex(index);
            attDTO.setExt(fullFileExtions);

            return attDTO;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BizRuntimeException("存储IO异常");
        }
    }

    @Override
    protected boolean handleFileDelete(FileInfoBO fileInfoBO) throws Exception {
        // 删除旧的文件
        getMinioClient().removeObject(RemoveObjectArgs.builder().bucket(fileInfoBO.getBucket()).object(fileInfoBO.getFilePath()).build());
        return true;
    }

    @Override
    protected FileInfoVO handleFileMetadata(FileInfoBO fileInfoBO, boolean metadata) {

        // 文件存储信息
        FileInfoVO attDTO = new FileInfoVO();
        attDTO.setBucket(fileInfoBO.getBucket());
        attDTO.setUuid(fileInfoBO.getFileId());
        attDTO.setName(fileInfoBO.getFileName());
        attDTO.setPath(fileInfoBO.getFilePath());
        attDTO.setExt(fileInfoBO.getFileExt());

        try {

            String url = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().bucket(fileInfoBO.getBucket()).object(fileInfoBO.getFilePath()).method(Method.GET).build());
            attDTO.setUrl(url);
            if (StringUtils.isNotBlank(exposedUrl)) {
                attDTO.setUrl(this.exposeUrl(url));
            }
            if (StringUtils.isNoneBlank(fileInfoBO.getFileThumb())) {
                attDTO.setThumb(fileInfoBO.getFileThumb());
                String thumbUrl = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().bucket(fileInfoBO.getBucket()).object(fileInfoBO.getFileThumb()).method(Method.GET).build());
                attDTO.setThumbUrl(thumbUrl);
                if (StringUtils.isNotBlank(exposedUrl)) {
                    attDTO.setThumbUrl(this.exposeUrl(thumbUrl));
                }
            }
			/*// 文件元数据
			try {
				ObjectStat objectStat = getMinioClient().statObject(StatObjectArgs.builder().bucket(entity.getBucket()).object(entity.getFilePath()).build());
				if(Objects.nonNull(objectStat)) {
					attDTO.setMetadata(objectStat..userMetadata().entrySet().stream().map(m -> {
						FileMetaData metaDTO = new FileMetaData();
						metaDTO.setName(m.getKey());
						metaDTO.setValue(m.getValue());
						return metaDTO;
					}).collect(Collectors.toSet()));
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}*/

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return attDTO;
    }

    @Override
    protected void handleFileDownload(FileInfoBO fileInfoBO, FileDownloadResult downloadRt) throws Exception {
        // 读取文件内容
        GetObjectArgs getObjectArgs = GetObjectArgs.builder().bucket(fileInfoBO.getBucket()).object(fileInfoBO.getFilePath()).build();
        InputStream stream = getMinioClient().getObject(getObjectArgs);
        downloadRt.setStream(stream);
        downloadRt.setBytes(IOUtils.toByteArray(stream));
    }


    private String exposeUrl(String innerNetworkUrl) {
        return innerNetworkUrl.replace(minioProperties.getEndpoint(), innerNetworkUrl);
    }

    public MinioClient getMinioClient() {
        return minioClient;
    }

}
