/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.oss.strategy;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.github.tobato.fastdfs.domain.fdfs.MetaData;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.domain.upload.FastImageFile;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.github.tobato.fastdfs.spring.boot.FastdfsTemplate;
import com.github.tobato.fastdfs.spring.boot.FileStorePath;
import com.google.common.collect.Sets;
import hitool.core.io.FilenameUtils;
import io.hiwepy.boot.api.exception.BizRuntimeException;
import io.hiwepy.boot.api.utils.CollectionUtils;
import io.hiwepy.cloud.oss.core.Constants;
import io.hiwepy.cloud.oss.core.bo.*;
import io.hiwepy.cloud.oss.core.enums.OssChannel;
import io.hiwepy.cloud.oss.core.strategy.AbstractFileStoreStrategy;
import io.hiwepy.cloud.oss.core.utils.OssUtils;
import io.hiwepy.cloud.oss.core.vo.FileInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class FastdfsFileStoreStrategy extends AbstractFileStoreStrategy {

    private DownloadByteArray callback = new DownloadByteArray();
    @Autowired
    private FastFileStorageClient fdfsStorageClient;
    @Autowired
    private FastdfsTemplate fdfsTemplate;

    @Override
    public OssChannel getChannel() {
        return OssChannel.OSS_FASTDFS;
    }

    @Override
    public FileStoreConfig getConfig() {
        FileStoreConfig config = new FileStoreConfig();
        config.setEndpoint(getFdfsTemplate().getEndpoint());
        return config;
    }

    @Override
    protected FileInfoVO handleFileUpload(FileStoreBO uploadBo, MultipartFile file, int index) throws Exception {
        try {

            // 文件存储结果
            StorePath storePath = this.storeFile(file, uploadBo.getThumbWidth(), uploadBo.getThumbHeight());

            // 文件存储信息
            String uuid = getSequence().nextId().toString();
            FileInfoVO attDTO = new FileInfoVO();
            attDTO.setUuid(uuid);
            attDTO.setName(file.getOriginalFilename());
            attDTO.setPath(storePath.getPath());
            attDTO.setUrl(getFdfsTemplate().getAccsssURL(storePath));
            if (storePath instanceof FileStorePath) {
                attDTO.setThumb(((FileStorePath) storePath).getThumb());
                attDTO.setThumbUrl(getFdfsTemplate().getThumbAccsssURL((FileStorePath) storePath));
            }
            attDTO.setSize(file.getSize());
            attDTO.setIndex(index);
            attDTO.setExt(FilenameUtils.getExtension(file.getOriginalFilename()));

            return attDTO;

        } catch (Exception e) {
            throw new BizRuntimeException("存储IO异常");
        }
    }

    @Override
    protected boolean handleFileDelete(FileInfoBO fileInfoBO) throws Exception {
        // 删除旧的文件
        getFdfsStorageClient().deleteFile(fileInfoBO.getBucket(), fileInfoBO.getFilePath());
        return true;
    }

    @Override
    protected FileInfoVO handleFileMetadata(FileInfoBO fileInfoBO, boolean metadata) {

        // 文件存储信息
        FileInfoVO attDTO = new FileInfoVO();

        attDTO.setUuid(fileInfoBO.getFileId());
        attDTO.setName(fileInfoBO.getFileName());
        attDTO.setPath(fileInfoBO.getFilePath());
        attDTO.setExt(fileInfoBO.getFileExt());

        try {
            if (StringUtils.isNoneBlank(fileInfoBO.getFileThumb())) {
                attDTO.setThumb(fileInfoBO.getFileThumb());
                attDTO.setThumbUrl(getFdfsTemplate().getAccsssURL(fileInfoBO.getBucket(), fileInfoBO.getFileThumb()));
            }
            attDTO.setUrl(getFdfsTemplate().getAccsssURL(fileInfoBO.getBucket(), fileInfoBO.getFilePath()));
            Set<MetaData> metaDatas = getFdfsStorageClient().getMetadata(fileInfoBO.getBucket(), fileInfoBO.getFilePath());
            if (!CollectionUtils.isEmpty(metaDatas)) {
                attDTO.setMetadata(metaDatas.stream().map(m -> {
                    FileMetaData metaDTO = new FileMetaData();
                    metaDTO.setName(m.getName());
                    metaDTO.setValue(m.getValue());
                    return metaDTO;
                }).collect(Collectors.toSet()));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return attDTO;

    }

    @Override
    protected void handleFileDownload(FileInfoBO fileInfoBO, FileDownloadResult downloadRt) throws Exception {

        // 文件元数据
        try {
            Set<MetaData> metaDatas = getFdfsStorageClient().getMetadata(fileInfoBO.getBucket(), fileInfoBO.getFilePath());
            if (!CollectionUtils.isEmpty(metaDatas)) {
                downloadRt.getFile().setMetadata(metaDatas.stream().map(m -> {
                    FileMetaData metaDTO = new FileMetaData();
                    metaDTO.setName(m.getName());
                    metaDTO.setValue(m.getValue());
                    return metaDTO;
                }).collect(Collectors.toSet()));
            }
        } catch (Exception e) {
        }

        // 读取文件内容
        byte[] bytes = getFdfsStorageClient().downloadFile(fileInfoBO.getBucket(), fileInfoBO.getFilePath(), callback);
        downloadRt.setBytes(bytes);

    }

    protected Set<MetaData> metaDataSet(MultipartFile file) {
        Set<MetaData> metaDataSet = Sets.newHashSet();
        try (InputStream inputStream = file.getInputStream();) {
            Metadata metadata = ImageMetadataReader.readMetadata(inputStream);
            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    //格式化输出[directory.getName()] - tag.getTagName() = tag.getDescription()
                    System.out.format("[%s] - %s = %s%n", directory.getName(), tag.getTagName(), tag.getDescription());
                    metaDataSet.add(new MetaData(directory.getName(), tag.getTagName()));
                }
                if (directory.hasErrors()) {
                    for (String error : directory.getErrors()) {
                        System.err.format("ERROR: %s", error);
                    }
                }
            }
        } catch (Exception e) {
        }
        return metaDataSet;
    }

    protected StorePath storeFile(MultipartFile file, Integer width, Integer height) throws IOException {
        try (InputStream inputStream = file.getInputStream()) {
            // 文件存储结果
            StorePath storePath = null;
            // 上传的是图片
            if (OssUtils.isImage(file)) {

                // 可生成缩略图的图片
                if (OssUtils.thumbable(file, width, height)) {
                    // 文件后缀
                    String extension = FilenameUtils.getExtension(file.getOriginalFilename());
                    // 文件上传信息
                    FastImageFile fastImageFile = new FastImageFile.Builder()
                            .withFile(inputStream, file.getSize(), extension)
                            .withThumbImage(width, height)
                            .toGroup(Constants.GROUP_NAME)
                            .build();

                    // 上传并且生成缩略图
                    storePath = getFdfsStorageClient().uploadImage(fastImageFile);

                    StringBuilder thumbPath = new StringBuilder();
                    thumbPath.append(FilenameUtils.getPath(storePath.getPath()));
                    thumbPath.append(FilenameUtils.getBaseName(storePath.getPath()));
                    thumbPath.append(fastImageFile.getThumbImage().getPrefixName());
                    thumbPath.append(".").append(extension);

                    storePath = new FileStorePath(storePath, thumbPath.toString());

                } else {
                    storePath = getFdfsStorageClient().uploadFile(Constants.GROUP_NAME, inputStream, file.getSize(),
                            FilenameUtils.getExtension(file.getOriginalFilename()));
                }
                // 保存文件元信息
                getFdfsStorageClient().mergeMetadata(storePath.getGroup(), storePath.getPath(), this.metaDataSet(file));
            } else {
                storePath = getFdfsStorageClient().uploadFile(Constants.GROUP_NAME, inputStream, file.getSize(),
                        FilenameUtils.getExtension(file.getOriginalFilename()));
            }
            return storePath;
        }
    }

    public FastFileStorageClient getFdfsStorageClient() {
        return fdfsStorageClient;
    }

    public FastdfsTemplate getFdfsTemplate() {
        return fdfsTemplate;
    }

}
