package io.hiwepy.cloud.sms.core.exception;


import io.hiwepy.boot.api.ApiCode;
import io.hiwepy.boot.api.exception.BizRuntimeException;

public class SmsSendRepeatException extends BizRuntimeException {

    public SmsSendRepeatException(ApiCode code, String i18n) {
        super(code, i18n);
    }

    public SmsSendRepeatException(int code, String msg, Throwable cause) {
        super(code, msg, cause);
    }

}
