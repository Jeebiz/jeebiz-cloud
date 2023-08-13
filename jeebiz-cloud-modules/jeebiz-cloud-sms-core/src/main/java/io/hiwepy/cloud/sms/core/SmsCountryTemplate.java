package io.hiwepy.cloud.sms.core;

import com.github.hiwepy.ip2region.spring.boot.IP2regionTemplate;
import org.apache.commons.lang3.StringUtils;
import org.nutz.plugins.ip2region.DataBlock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Lazy
public class SmsCountryTemplate {

    @Autowired
    private IP2regionTemplate template;

    public String getCountryByIp(String ip) {
        if (StringUtils.isBlank(ip)) {
            return null;
        }
        DataBlock dataBlock = null;
        try {
            dataBlock = template.binarySearch(ip);
            String region = dataBlock.getRegion();
            return region.substring(0, region.indexOf("|"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
