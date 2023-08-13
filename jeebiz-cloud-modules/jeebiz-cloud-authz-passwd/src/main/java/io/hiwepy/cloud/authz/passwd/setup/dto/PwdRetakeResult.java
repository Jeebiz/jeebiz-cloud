package io.hiwepy.cloud.authz.passwd.setup.dto;

import java.util.Map;

public class PwdRetakeResult {

    protected String status;
    protected String msgKey;
    protected Map<String, Object> data;

    public PwdRetakeResult(String status, String msgKey) {
        this.status = status;
        this.msgKey = msgKey;

    }

    public PwdRetakeResult(String status, String msgKey, Map<String, Object> data) {
        this.status = status;
        this.msgKey = msgKey;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsgKey() {
        return msgKey;
    }

    public void setMsgKey(String msgKey) {
        this.msgKey = msgKey;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public static PwdRetakeResult to(String status, String msgKey) {
        return new PwdRetakeResult(status, msgKey);
    }

    public static PwdRetakeResult to(String status, String msgKey, Map<String, Object> data) {
        return new PwdRetakeResult(status, msgKey, data);
    }

}
