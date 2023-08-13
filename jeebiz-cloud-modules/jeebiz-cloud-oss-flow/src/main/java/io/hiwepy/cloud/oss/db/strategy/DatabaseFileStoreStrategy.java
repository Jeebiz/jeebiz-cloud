/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.oss.db.strategy;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import hitool.core.io.FilenameUtils;
import hitool.core.lang3.time.DateUtils;
import io.hiwepy.cloud.oss.core.OssRedisKey;
import io.hiwepy.cloud.oss.core.bo.FileDownloadResult;
import io.hiwepy.cloud.oss.core.bo.FileInfoBO;
import io.hiwepy.cloud.oss.core.bo.FileStoreBO;
import io.hiwepy.cloud.oss.core.bo.FileStoreConfig;
import io.hiwepy.cloud.oss.core.enums.OssChannel;
import io.hiwepy.cloud.oss.core.strategy.AbstractFileStoreStrategy;
import io.hiwepy.cloud.oss.core.utils.OssUtils;
import io.hiwepy.cloud.oss.core.vo.FileInfoVO;
import io.hiwepy.cloud.oss.flow.dao.FileObjectMapper;
import io.hiwepy.cloud.oss.flow.dao.entities.FileObjectEntity;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.Duration;
import java.util.Objects;
import java.util.stream.Stream;

@Component
@Slf4j
public class DatabaseFileStoreStrategy extends AbstractFileStoreStrategy {

    @Autowired
    private FileObjectMapper fileObjectMapper;

    @Override
    public OssChannel getChannel() {
        return OssChannel.OSS_DATABASE;
    }

    @Override
    public FileStoreConfig getConfig() {
        FileStoreConfig config = new FileStoreConfig();
        config.setEndpoint(getOssProperties().getLocal().getEndpoint());
        return config;
    }

    @Override
    protected FileInfoVO handleFileUpload(FileStoreBO uploadBo, MultipartFile file, int index) throws Exception {


        String uuid = getSequence().nextId().toString();
        String basename = DateUtils.getDate() + File.separator + uuid;
        String path = basename + FilenameUtils.getFullExtension(file.getOriginalFilename());
        log.info("File uuid : {}", uuid);
        log.info("File basename : {}", basename);
        log.info("File size : {}", file.getSize());
        log.info("File path : {}", path);

        // 保存缩略图
        String imageBase64 = null;
        if (uploadBo.isThumb() && OssUtils.isImage(file)) {
            try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
                Thumbnails.of(file.getInputStream()).scale(uploadBo.getThumbWidth(), uploadBo.getThumbHeight()).toOutputStream(output);
                FileObjectEntity fileInfoBO = FileObjectEntity.builder().fileId(uuid).fileObject(output.toByteArray()).build();
                fileObjectMapper.insert(fileInfoBO);
                imageBase64 = OssUtils.toBase64(file.getOriginalFilename(), fileInfoBO.getFileObject());
            }
        } else {
            FileObjectEntity fileInfoBO = FileObjectEntity.builder().fileId(uuid).fileObject(file.getBytes()).build();
            fileObjectMapper.insert(fileInfoBO);
            imageBase64 = OssUtils.toBase64(file.getOriginalFilename(), fileInfoBO.getFileObject());
        }

        // 文件存储信息
        FileInfoVO attDTO = new FileInfoVO();
        attDTO.setBucket(uploadBo.getBizId());
        attDTO.setUuid(uuid);
        attDTO.setName(file.getOriginalFilename());
        attDTO.setPath(path);
        attDTO.setSize(file.getSize());
        attDTO.setIndex(index);
        attDTO.setExt(FilenameUtils.getExtension(file.getOriginalFilename()));
        attDTO.setBase64(imageBase64);
        return attDTO;
    }

    @Override
    protected void afterRecordFileEntity(FileInfoBO fileInfoBO, MultipartFile file, FileInfoVO fileInfoVO) throws IOException {
        super.afterRecordFileEntity(fileInfoBO, file, fileInfoVO);
        // 图片进行缓存
        if (getOssProperties().getCache().isEnabled()) {
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            if (ArrayUtils.isNotEmpty(getOssProperties().getCache().getExtensions())
                    && Stream.of(getOssProperties().getCache().getExtensions()).anyMatch(ext -> StringUtils.equalsIgnoreCase(ext, extension))
                    && OssUtils.isImage(file)) {
                String imageBase64 = OssUtils.toBase64(file);
                Duration timeout = Objects.isNull(getOssProperties().getCache().getTimeout()) ? Duration.ofDays(1) : getOssProperties().getCache().getTimeout();
                getRedisOperation().set(OssRedisKey.UPLOAD_IMAGE_DATAS.getKey(fileInfoBO.getFileId()), imageBase64, timeout);
            }
        }
    }

    @Override
    protected boolean handleFileDelete(FileInfoBO fileInfoBO) throws Exception {
        int rt = fileObjectMapper.delete(new LambdaQueryWrapper<FileObjectEntity>().eq(FileObjectEntity::getFileId, fileInfoBO.getFileId()));
        getRedisOperation().del(OssRedisKey.UPLOAD_IMAGE_DATAS.getKey(fileInfoBO.getFileId()));
        return SqlHelper.retBool(rt);
    }

    @Override
    protected FileInfoVO handleFileMetadata(FileInfoBO fileInfoBO, boolean metadata) {
        // 文件存储信息
        FileInfoVO fileInfoVO = new FileInfoVO();

        fileInfoVO.setUuid(fileInfoBO.getFileId());
        fileInfoVO.setName(fileInfoBO.getFileName());
        fileInfoVO.setPath(fileInfoBO.getFilePath());
        fileInfoVO.setExt(fileInfoBO.getFileExt());

        FileObjectEntity fileObject = fileObjectMapper.selectOne(new LambdaQueryWrapper<FileObjectEntity>().eq(FileObjectEntity::getFileId, fileInfoBO.getFileId()));
        if (Objects.nonNull(fileObject)) {

            String extension = FilenameUtils.getExtension(fileInfoBO.getFileName());
            if (ArrayUtils.isNotEmpty(getOssProperties().getCache().getExtensions())
                    && Stream.of(getOssProperties().getCache().getExtensions()).anyMatch(ext -> StringUtils.equalsIgnoreCase(ext, extension))) {

                String imageBase64 = getRedisOperation().getString(OssRedisKey.UPLOAD_IMAGE_DATAS.getKey(fileInfoBO.getFileId()));
                if (StringUtils.isBlank(imageBase64)) {
                    imageBase64 = OssUtils.toBase64(fileInfoBO.getFileName(), fileObject.getFileObject());
                    Duration timeout = Objects.isNull(getOssProperties().getCache().getTimeout()) ? Duration.ofDays(1) : getOssProperties().getCache().getTimeout();
                    getRedisOperation().set(OssRedisKey.UPLOAD_IMAGE_DATAS.getKey(fileInfoBO.getFileId()), imageBase64, timeout);
                }
                fileInfoVO.setBase64(imageBase64);
            }
            // 文件元数据
            if (metadata) {
                try (InputStream inputStream = new ByteArrayInputStream(fileObject.getFileObject())) {
                    fileInfoVO.setMetadata(OssUtils.metaDataSet(inputStream));
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        return fileInfoVO;
    }

    @Override
    protected void handleFileDownload(FileInfoBO fileInfoBO, FileDownloadResult downloadRt) throws Exception {
        FileObjectEntity fileObject = fileObjectMapper.selectOne(new LambdaQueryWrapper<FileObjectEntity>().eq(FileObjectEntity::getFileId, fileInfoBO.getFileId()));
        if (Objects.nonNull(fileObject)) {
            downloadRt.setBytes(fileObject.getFileObject());
        }
    }

}
