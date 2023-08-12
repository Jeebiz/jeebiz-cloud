package io.hiwepy.cloud.sms.core.exception;


import io.hiwepy.boot.api.ApiCode;
import io.hiwepy.boot.api.exception.BizRuntimeException;

public class SmsSendException extends BizRuntimeException {

    public SmsSendException(ApiCode code, String i18n) {
        super(code, i18n);
    }

    public SmsSendException(int code, String msg, Throwable cause) {
        super(code, msg, cause);
    }

}
