package io.hiwepy.cloud.sample.setup;

import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.api.config.annotation.NacosConfigurationProperties;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@RefreshScope
@NacosConfigurationProperties(dataId = "sample-common.yaml", autoRefreshed = true, type = ConfigType.YAML)
public class CommonProperteis {

    /**
     * 固定密钥
     */
    @Value("${fixed-secret:hzty@2022}")
    private String fixedSecret;
    /**
     * 是否开启签名，true-开启，false-关闭
     */
    @Value("${sign-request:false}")
    private boolean signRequest;
    /**
     * 图片访问域名
     */
    @Value("${image-host:''}")
    private String imageHost;
    /**
     * 使用缓存
     */
    @Value("${use-cache:true}")
    private boolean useCache;
    /**
     * 清理缓存
     */
    @Value("${clean-cache:false}")
    private boolean cleanCache;

}
