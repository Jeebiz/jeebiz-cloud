package io.hiwepy.cloud.sample.setup.config;

import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.api.config.annotation.NacosConfigurationProperties;
import io.hiwepy.cloud.api.Constants;
import lombok.Data;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@RefreshScope
@NacosConfigurationProperties(dataId = "jeebiz-common", autoRefreshed = true, type = ConfigType.YAML)
@ConfigurationProperties(prefix = "jeebiz-common")
public class CommonProperteis {

    @Value(value = "${imBigGroupId:''}")
    private String imBigGroupId;

    @Value(value = "${imgUrl:https://jeebiz.oss-accelerate.aliyuncs.com/}")
    private String imgUrl;

    @Value(value = "${imgObscure:/blur,r_50,s_50/resize,w_200/quality,Q_60}")
    private String imgObscure;

    @Value(value = "${imgResize:/resize,w_200}")
    private String imgResize;

    @Value(value = "${refreshCache:false}")
    private boolean refreshCache;

    public String getImgPrefix(String appVer) {
        return imgUrl;
    }

    public String getImgUrl(String url) {
        String format = String.format(Constants.OSS_IMAGE_FORMAT, FilenameUtils.getExtension(url).toLowerCase());
        return imgUrl.concat(url).concat(format);
    }

    public String getImgObscureUrl(String url) {
        String format = String.format(Constants.OSS_IMAGE_FORMAT, FilenameUtils.getExtension(url).toLowerCase());
        return imgUrl.concat(url).concat(format).concat(imgObscure);
    }

    public String getAvatarUrl(String url) {
        String format = String.format(Constants.OSS_IMAGE_FORMAT, FilenameUtils.getExtension(url).toLowerCase());
        return imgUrl.concat(url).concat(format).concat(imgResize);
    }

    public String getImgUrl(String appVer, String url) {
        String format = String.format(Constants.OSS_IMAGE_FORMAT, FilenameUtils.getExtension(url).toLowerCase());
        return imgUrl.concat(url).concat(format);
    }

    public String getReplaceUrl(String url) {
        String format = String.format(Constants.OSS_IMAGE_FORMAT, FilenameUtils.getExtension(url.split("\\?")[0]).toLowerCase());
        return url.replace(this.getImgUrl(), "").replace(format, "");
    }

    public String getSnapshotUrl(String url, Integer width, Integer height) {
        // ) + "?x-oss-process=video/snapshot," + "m_fast,ar_h,t_1,f_jpg," + "w_"+
        String process = new StringBuilder("?x-oss-process=video/snapshot,m_fast,")
                .append(width > height ? "ar_w," : "ar_h,")
                .append("t_1,f_jpg,")
                .append("w_").append(width).append(",")
                .append("h_").append(height).toString();
        return imgUrl.concat(url).concat(process);
    }

}
