/** 
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved. 
 */
package io.hiwepy.cloud.sample.setup.feign;

import com.google.common.collect.Lists;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.hiwepy.boot.api.XHeaders;
import io.hiwepy.boot.api.utils.SignUtils;
import io.hiwepy.boot.api.utils.WebUtils;
import io.hiwepy.cloud.sample.setup.CommonProperteis;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map.Entry;
import java.util.Objects;

@Slf4j
@Component
public class FeignRequestSignInterceptor implements RequestInterceptor {

	private static final NonWebRequestAttributes NON_WEB_REQUEST_ATTRIBUTES = new NonWebRequestAttributes();
	
	private CommonProperteis commonProperteis;

    @Override
    public void apply(RequestTemplate template) {
    	// Sign 未开启
		if(commonProperteis.isSignRequest()) {
			return;
		}
    	HttpServletRequest request = WebUtils.getHttpServletRequest();
    	if(request != null) {
            String token = "";
            Enumeration<String> enumeration = request.getHeaderNames();
            while (enumeration.hasMoreElements()) {
                String key = enumeration.nextElement();
                if (XHeaders.X_AUTHORIZATION.equalsIgnoreCase(key)) {
                	token = request.getHeader(key);
                    break;
                }
            }
            
            String appId = request.getHeader(XHeaders.X_APP_ID);
    		String appChannel = request.getHeader(XHeaders.X_APP_CHANNEL);
    		String appVersion = request.getHeader(XHeaders.X_APP_VERSION);

    		log.info(XHeaders.X_APP_ID + "：{}", appId);
    		log.info(XHeaders.X_APP_CHANNEL + "：{}", appChannel);
    		log.info(XHeaders.X_APP_VERSION + "：{}", appVersion);
    		
            String salt = SignUtils.salt(appId, appVersion, appChannel, commonProperteis.getFixedSecret());
            
            // 通过template获取到请求参数
            MultiValueMap<String, String> formData = null;
			if(MapUtils.isNotEmpty(template.queries())) {
				formData = new LinkedMultiValueMap<>();
				for (Entry<String, Collection<String>> entry : template.queries().entrySet()) {
	            	formData.put(entry.getKey(), Lists.newArrayList(entry.getValue()));
				}
			}
            
            // 通过template获取到请求体（已经被转成json）
			String jsonBody = Objects.nonNull(template.body()) ? new String(template.body()) : StringUtils.EMPTY;
            
            try {
				String sign = SignUtils.sign(token, formData, jsonBody, salt);
				template.header(XHeaders.X_SIGN, sign);
			} catch (UnsupportedEncodingException e) {
				template.header(XHeaders.X_SIGN, "");
				e.printStackTrace();
			}
    	} else {
            template.header(XHeaders.X_SIGN, "");
		}
    }


}
