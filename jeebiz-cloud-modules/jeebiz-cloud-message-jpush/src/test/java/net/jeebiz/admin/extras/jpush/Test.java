/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.base.jpush;

import io.hiwepy.cloud.message.jpush.JPushOperationTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class Test {

    @Autowired
    private JPushOperationTemplate jPushOperationTemplate;

    public static void main(String[] args) {
		/*
        PushObject pushObject = new PushObject();
        pushObject.setMsgContent(content);
        pushObject.setAlert(alert);
        jPushOperationTemplate.send(userId,tag,pushObject);
	    */
    }

}
