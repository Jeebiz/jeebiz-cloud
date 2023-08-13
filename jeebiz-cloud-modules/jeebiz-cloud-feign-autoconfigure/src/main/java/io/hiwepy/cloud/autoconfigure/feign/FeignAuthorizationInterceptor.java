/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.autoconfigure.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.hiwepy.boot.api.XHeaders;
import io.hiwepy.boot.api.utils.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

public class FeignAuthorizationInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        HttpServletRequest request = WebUtils.getHttpServletRequest();
        if (request != null) {
            String s = null;
            Enumeration<String> enumeration = request.getHeaderNames();
            while (enumeration.hasMoreElements()) {
                String key = enumeration.nextElement();
                if (XHeaders.X_AUTHORIZATION.equalsIgnoreCase(key)) {
                    s = request.getHeader(key);
                    break;
                }
            }
            template.header(XHeaders.X_AUTHORIZATION, s);
        } else {
            template.header(XHeaders.X_AUTHORIZATION, "");
        }
    }

}
