/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.oss.core.strategy.local;

import hitool.core.io.FilenameUtils;
import hitool.core.lang3.time.DateUtils;
import io.hiwepy.boot.api.exception.BizRuntimeException;
import io.hiwepy.cloud.oss.core.bo.*;
import io.hiwepy.cloud.oss.core.enums.OssChannel;
import io.hiwepy.cloud.oss.core.strategy.AbstractFileStoreStrategy;
import io.hiwepy.cloud.oss.core.utils.AttUtils;
import io.hiwepy.cloud.oss.core.utils.OssUtils;
import io.hiwepy.cloud.oss.core.vo.FileInfoVO;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

@Component
@Slf4j
public class LocalFileStoreStrategy extends AbstractFileStoreStrategy {

    @Override
    public OssChannel getChannel() {
        return OssChannel.OSS_LOCAL;
    }

    @Override
    public FileStoreConfig getConfig() {
        FileStoreConfig config = new FileStoreConfig();
        config.setEndpoint(getOssProperties().getLocal().getEndpoint());
        return config;
    }

    @Override
    protected FileInfoVO handleFileUpload(FileStoreBO uploadBo, MultipartFile file, int index) throws Exception {
        try (InputStream inputStream = file.getInputStream();) {

            // 文件存储目录
            File fileDir = AttUtils.getTargetDir(getOssProperties().getLocal().getUserDir(), uploadBo.getBizId());
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }

            String uuid = getSequence().nextId().toString();
            String basename = DateUtils.getDate() + File.separator + uuid;
            String path = basename + FilenameUtils.getFullExtension(file.getOriginalFilename());

            File tFile = new File(fileDir, path);
            if (!tFile.getParentFile().exists()) {
                tFile.getParentFile().mkdirs();
            }
            ;
            // 当MultipartFile执行保存的时候（MultipartFile.transferTo(file)），会把临时文件 删除 ，再执行MultipartFile.getSize()的时候，就会出现找不到临时文件的异常。
            // file.transferTo(tFile);
            FileUtils.copyInputStreamToFile(inputStream, tFile);

            // 上传的是图片且可生成缩略图的图片
            String thumbPath = null;
            if (OssUtils.isImage(file) && OssUtils.thumbable(file, uploadBo.getThumbWidth(), uploadBo.getThumbHeight())) {
                // 保存缩略图
                thumbPath = basename + new ThumbImageBO(uploadBo.getThumbWidth(), uploadBo.getThumbHeight()).getPrefixName() + FilenameUtils.getFullExtension(file.getOriginalFilename());
                try (FileOutputStream output = new FileOutputStream(new File(fileDir, thumbPath))) {
                    Thumbnails.of(inputStream).scale(uploadBo.getThumbWidth(), uploadBo.getThumbHeight()).toOutputStream(output);
                }
            }

            // 文件存储信息
            FileInfoVO attDTO = new FileInfoVO();
            attDTO.setBucket(uploadBo.getBizId());
            attDTO.setUuid(uuid);
            attDTO.setName(file.getOriginalFilename());
            attDTO.setPath(path);
            //attDTO.setUrl(getOssTemplate().getAccsssURL(storePath));
            if (StringUtils.isNotBlank(thumbPath)) {
                attDTO.setThumb(thumbPath);
                //attDTO.setThumbUrl(getOssTemplate().getThumbAccsssURL(storePath));
            }
            attDTO.setSize(tFile.length());
            attDTO.setIndex(index);
            attDTO.setExt(FilenameUtils.getExtension(file.getOriginalFilename()));

            return attDTO;

        } catch (Exception e) {
            throw new BizRuntimeException("存储IO异常");
        }
    }

    @Override
    protected boolean handleFileDelete(FileInfoBO fileInfoBO) throws Exception {
        // 文件存储目录
        File fileDir = AttUtils.getTargetDir(getOssProperties().getLocal().getUserDir(), fileInfoBO.getBizId());
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        ;
        // 删除旧的文件
        File oldFile = new File(fileDir, fileInfoBO.getFilePath());
        if (oldFile.exists()) {
            oldFile.delete();
        }
        return true;
    }

    @Override
    protected FileInfoVO handleFileMetadata(FileInfoBO fileInfoBO, boolean metadata) {

        File fileDir = AttUtils.getTargetDir(getOssProperties().getLocal().getUserDir(), fileInfoBO.getBucket());
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        ;

        // 文件存储信息
        FileInfoVO fileDTO = new FileInfoVO();

        fileDTO.setUuid(fileInfoBO.getFileId());
        fileDTO.setName(fileInfoBO.getFileName());
        fileDTO.setPath(fileInfoBO.getFilePath());
        //attDTO.setUrl(getFdfsTemplate().getAccsssURL(entity.getGroup(), entity.getFilePath()));
        if (StringUtils.isNoneBlank(fileInfoBO.getFileThumb())) {
            fileDTO.setThumb(fileInfoBO.getFileThumb());
            //attDTO.setThumbUrl(getFdfsTemplate().getAccsssURL(entity.getGroup(), entity.getFileThumb()));
        }
        fileDTO.setExt(fileInfoBO.getFileExt());
        // 文件元数据
        if (metadata) {
            try {
                fileDTO.setMetadata(OssUtils.metaDataSet(new File(fileDir, fileInfoBO.getFilePath())));
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }

        return fileDTO;
    }

    @Override
    protected void handleFileDownload(FileInfoBO fileInfoBO, FileDownloadResult downloadRt) throws Exception {

        File fileDir = AttUtils.getTargetDir(getOssProperties().getLocal().getUserDir(), fileInfoBO.getBucket());
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        ;

        downloadRt.setBytes(FileCopyUtils.copyToByteArray(new File(fileDir, fileInfoBO.getFilePath())));

    }


}
