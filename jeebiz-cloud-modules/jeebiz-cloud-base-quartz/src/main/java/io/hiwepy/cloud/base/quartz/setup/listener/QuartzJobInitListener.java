/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.base.quartz.setup.listener;

import io.hiwepy.cloud.base.quartz.service.IQuartzJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 1)
public class QuartzJobInitListener implements CommandLineRunner {

    @Autowired
    private IQuartzJobService quartzJobService;

    @Override
    public void run(String... arg0) throws Exception {
        try {
            getQuartzJobService().initJob();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public IQuartzJobService getQuartzJobService() {
        return quartzJobService;
    }

}