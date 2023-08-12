package io.hiwepy.cloud.oss.core.strategy;

import io.hiwepy.cloud.oss.core.bo.FileDeleteBO;
import io.hiwepy.cloud.oss.core.bo.FileUploadBO;
import io.hiwepy.cloud.oss.core.bo.FilesUploadBO;
import io.hiwepy.cloud.oss.core.enums.OssChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class FileStoreStrategyRouter implements InitializingBean {

    private Map<OssChannel, FileStoreStrategy> map = new HashMap<>();

    @Autowired(required = false)
    private List<FileStoreStrategy> fileioStrategyList;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (!CollectionUtils.isEmpty(fileioStrategyList)) {
            for (FileStoreStrategy strategy : fileioStrategyList) {
                map.put(strategy.getChannel(), strategy);
            }
        }
        log.info("fileioStrategyMap:{}", map);
    }

    public FileStoreStrategy routeFor(OssChannel channel) {
        FileStoreStrategy orderStrategy = map.get(channel);
        return orderStrategy;
    }

    public <O extends FileUploadBO> FileStoreStrategy routeFor(O smsBo) {
        FileStoreStrategy orderStrategy = map.get(smsBo.getChannel());
        return orderStrategy;
    }

    public <O extends FilesUploadBO> FileStoreStrategy routeFor(O smsBo) {
        FileStoreStrategy orderStrategy = map.get(smsBo.getChannel());
        return orderStrategy;
    }

    public <O extends FileDeleteBO> FileStoreStrategy routeFor(O smsBo) {
        FileStoreStrategy orderStrategy = map.get(smsBo.getChannel());
        return orderStrategy;
    }

}
