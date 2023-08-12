package io.hiwepy.cloud.oss.core.strategy;

import io.hiwepy.cloud.oss.core.bo.FileInfoBO;
import io.hiwepy.cloud.oss.core.enums.OssChannel;

public interface FileInfoProvider {

    FileInfoBO getFileInfo(OssChannel channel, String uuid) throws Exception;

    FileInfoBO deleteById(OssChannel channel, String uuid) throws Exception;

    default void recordFlow(OssChannel channel, FileInfoBO fileInfoBO) throws Exception {

    }

    ;

}
