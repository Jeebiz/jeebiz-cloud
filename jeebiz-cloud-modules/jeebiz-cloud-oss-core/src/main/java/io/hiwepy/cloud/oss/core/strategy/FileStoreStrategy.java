package io.hiwepy.cloud.oss.core.strategy;

import io.hiwepy.cloud.oss.core.bo.*;
import io.hiwepy.cloud.oss.core.enums.OssChannel;
import io.hiwepy.cloud.oss.core.vo.FileInfoVO;

/**
 * 短信发送策略
 */
public interface FileStoreStrategy {

    /**
     * 获取发送渠道
     *
     * @return
     */
    OssChannel getChannel();

    /**
     * FileStore Config
     *
     * @return
     */
    FileStoreConfig getConfig();

    <O extends FileUploadBO> FileUploadResult upload(O uploadBo) throws Exception;

    <O extends FilesUploadBO> FilesUploadResult upload(O uploadBo) throws Exception;

    <O extends FileReuploadBO> FileReuploadResult reupload(O uploadBo) throws Exception;

    boolean delete(FileInfoBO fileInfoBO) throws Exception;

    /**
     * 根据给出的文件相对路径下载文件
     *
     * @param fileInfoBO 要下载的文件对象
     * @return
     */
    FileDownloadResult download(FileInfoBO fileInfoBO) throws Exception;

    FileInfoVO getFileInfo(FileInfoBO fileInfoBO, boolean metadata);


}
