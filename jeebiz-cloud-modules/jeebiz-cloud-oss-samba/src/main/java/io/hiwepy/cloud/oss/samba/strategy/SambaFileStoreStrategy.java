package io.hiwepy.cloud.oss.samba.strategy;

import io.hiwepy.cloud.oss.core.bo.FileDownloadResult;
import io.hiwepy.cloud.oss.core.bo.FileInfoBO;
import io.hiwepy.cloud.oss.core.bo.FileStoreBO;
import io.hiwepy.cloud.oss.core.bo.FileStoreConfig;
import io.hiwepy.cloud.oss.core.enums.OssChannel;
import io.hiwepy.cloud.oss.core.strategy.AbstractFileStoreStrategy;
import io.hiwepy.cloud.oss.core.vo.FileInfoVO;
import org.springframework.web.multipart.MultipartFile;

public class SambaFileStoreStrategy extends AbstractFileStoreStrategy {

    @Override
    public OssChannel getChannel() {
        return OssChannel.OSS_SAMBA;
    }

    @Override
    public FileStoreConfig getConfig() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected FileInfoVO handleFileUpload(FileStoreBO uploadBo, MultipartFile file, int index) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected boolean handleFileDelete(FileInfoBO fileInfoBO) throws Exception {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    protected FileInfoVO handleFileMetadata(FileInfoBO fileInfoBO, boolean metadata) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void handleFileDownload(FileInfoBO fileInfoBO, FileDownloadResult downloadRt) throws Exception {
        // TODO Auto-generated method stub

    }

}
