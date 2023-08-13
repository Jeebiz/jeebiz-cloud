/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.autoconfigure.feign;

import org.springframework.web.context.request.RequestAttributes;

public class NonWebRequestAttributes implements RequestAttributes {

    @Override
    public Object getAttribute(String name, int scope) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setAttribute(String name, Object value, int scope) {
        // TODO Auto-generated method stub

    }

    @Override
    public void removeAttribute(String name, int scope) {
        // TODO Auto-generated method stub

    }

    @Override
    public String[] getAttributeNames(int scope) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback, int scope) {
        // TODO Auto-generated method stub

    }

    @Override
    public Object resolveReference(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getSessionId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object getSessionMutex() {
        // TODO Auto-generated method stub
        return null;
    }

}
