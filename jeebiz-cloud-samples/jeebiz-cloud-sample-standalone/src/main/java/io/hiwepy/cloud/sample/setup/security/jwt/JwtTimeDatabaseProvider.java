/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.sample.setup.security.jwt;

import com.github.hiwepy.jwt.time.JwtTimeProvider;
import io.hiwepy.cloud.sample.dao.CommonMapper;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;

public class JwtTimeDatabaseProvider implements JwtTimeProvider {

    private CommonMapper commonDao;

    public JwtTimeDatabaseProvider(CommonMapper commonDao) {
        super();
        this.commonDao = commonDao;
    }

    @Override
    public long now() {
        try {
            String nowString = getCommonDao().getNow();
            return DateUtils.parseDate(nowString, "yyyy-MM-dd HH:mm:ss").getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return System.currentTimeMillis();
    }

    public CommonMapper getCommonDao() {
        return commonDao;
    }

}
