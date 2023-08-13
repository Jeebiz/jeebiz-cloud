package io.hiwepy.cloud.authz.login.exception;

import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.api.Constants;
import io.hiwepy.boot.api.CustomApiCode;
import io.hiwepy.boot.api.exception.BizRuntimeException;
import org.springframework.biz.context.NestedMessageSource;
import org.springframework.biz.utils.SpringContextUtils;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * 1、通用异常参数枚举
 * a、国际化key 是大写的字符 _ 拼接，统一使用小写的如，api.not.start
 * b、变量按服务区分，统一加上服务名， 比如 third.api.fail 就是 third 服务的，user.info.save.fail 就表示 user 服务的
 * c、编码按服务进行分段，比如
 * common：10000 - 20000，
 * user：20000+
 */
public enum BizExceptionCode implements CustomApiCode {

    // common

    SIGN_MISSING("1403", 1403, "Signature information missing", "签名信息缺失"),

    SUCCESS_V1("SUCCESS", 200, "success", "SUCCESS"),
    FAILED("FAILED", 1000, "failed", "失败"),
    REPEATED("REPEATED", 2000, "repeated submit", "repeated submit"),
    SYSTEM_ERROR("system.error", 9999, "system error", "流量过大系统开小差啦，请尝试重新发起"),
    REMOTE_INVOKE_ERROR("remote.invoke.error", 9998, "remoteInvokeError", "远程调用失败"),
    MESSAGE_KEY_IS_NULL("message.key.is.null", 9997, "messageKeyIsNull", "messageKey不能为空"),
    DATABASE_FILTERS_EXISTS("database.filters.exists", 9996, "", ""),
    PARAM_NOT_EXISTS("param.not.exists", 9995, "", ""),

    // 短信相关
    SMS_SEND_REGION_REQUIRED("sms.send.region.required", 1502, "sms send region required", "请选择国家或地区"),
    SMS_PHONE_ERROR("sms.phone.error", 1502, "sms send region required", "手机号码不合法"),
    SMS_PHONE_MAX_ERROR("sms.phone.max.error", 1504, "phone is error", "验证码发送次数已超过上限"),
    SMS_ERROR("sms.send.backlist.limit", 1505, "sms error", "验证码发送失败请重试"),
    SMS_CODE_ERROR("sms.check.fail", 1506, "验证码验证失败", "验证码验证失败"),

    DEVICE_BAN("login.device.ban", 20017, "Your device has been banned from logging in, please contact customer service",
            "该设备已封禁，请联系客服"),
    SIGN_EXPIRED("login.token.expired", 20018, "Sign expired,Please log in again", "签名过期"),

    // 用户相关 30000-40000
    SAVE_USER_INFO_FAIL("user.info.save.fail", 30000, "保存用户信息失败", "保存用户信息失败"),
    USER_NOT_EXISTS("user.info.is.not.exsit", 30001, "用户未认证", "用户未认证"),
    ACCOUNT_NOT_EXISTS("account.info.is.not.exsit", 30001, "账号信息不存在", "账号信息不存在"),
    ACCOUNT_EXISTS("user.account.is.exsit", 30003, "用户名已存在", "用户名已存在"),
    USER_INFO_IS_NOT_EXSIT("user.info.is.not.exsit", 30001, "用户不存在", "用户不存在"),
    THIRD_SCAN_NOT_EXISTS("third.account.is.not exsit", 30002, "未绑定第三方账户", "未绑定第三方账户"),
    SECURE_INFO_ERROR("user.secure.info is error", 30004, "认证信息有误", "认证信息有误"),
    SECURE_INFO_USED("user.secure.info is used by other", 30005, "该用户已被他人绑定", "该用户已被他人绑定"),
    USER_OR_PASSWORD_IS_NOT_EXIST("user.info.password is.not.exsit", 30006, "用户名或密码错误", "用户名或密码错误"),
    DINGDING_NOT_CONFIG("dingding.not.config", 30007, "机构钉钉信息未配置", "机构钉钉信息未配置"),
    USER_MOBILE_NOT_UNIQUE("user.mobile.not.unique", 30008, "出现重复手机号", "出现重复手机号"),
    USER_MOBILE_NOT_FOUND("user.mobile.is.not.exsit", 30009, "通过手机号未找到用户", "通过手机号未找到用户"),
    USER_IDENTITY_NON_FOUND("user.identity.non.found", 30009, "用户未指定有效身份", "用户未指定有效身份"),
    USER_IDENTITY_NON_MATCH("user.identity.non.match", 30009, "用户身份不匹配", "用户身份不匹配"),
    UPDATE_PASSWORD_FAIL("update.password.fail", 30010, "修改密码失败", "修改密码失败"),
    REPEAT_USER("user.repeat.error", 30014, "出现多名重复的用户", "出现多名重复的用户"),
    NO_MATCH_CHILD("child.match.error", 30015, "未匹配到孩子", "未匹配到孩子"),
    NO_MATCH_STUDENT("student.match.error", 30016, "请联系管理员添加学生", "请联系管理员添加学生"),
    NO_MATCH_TEACHER("teacher.match.error", 30017, "请联系管理员添加教职工", "请联系管理员添加教职工"),


    USER_IS_LOCKED("user.is.locked", 30010, "用户已被锁定", "用户已被锁定"),
    DINGDING_CONFIG_ERROR("dingding.error.config", 30011, "机构钉钉信息配置有误", "机构钉钉信息配置有误"),
    THIRD_USER_HAS_BOUND("third.user.has.bound", 30012, "已经绑定过其他用户", "已经绑定过其他用户"),
    DINGDING_USER_NOT_ADMIN("dingding.user.not.admin", 30013, "当前账号无管理员权限", "当前账号无管理员权限"),

    ACCOUNT_IS_LOCKED("account.is.locked", 30013, "账户已被锁定", "账户已被锁定"),
    ;

    /**
     * 国际化Key
     */
    private String i18nKey;

    /**
     * 错误编号
     */
    private int errorCode;

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 错误描述
     */
    private String desc;


    private BizExceptionCode(String i18nKey, int errorCode, String errorMsg, String desc) {
        this.i18nKey = i18nKey;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.desc = desc;
    }

    public void throwException() {
        throw this.asException();
    }

    public static void throwByErrorcode(Integer errorCode) {
        getByErrorcode(errorCode).throwException();
    }

    public static BizExceptionCode getByErrorcode(Integer errorCode) {
        if (errorCode == null) {
            return BizExceptionCode.SYSTEM_ERROR;
        }
        BizExceptionCode[] errorCodes = values();

        for (BizExceptionCode bizErrorCode : errorCodes) {
            if (errorCode.equals(bizErrorCode.getErrorCode())) {
                return bizErrorCode;
            }
        }
        return BizExceptionCode.SYSTEM_ERROR;
    }

    public BizRuntimeException asException() {
        return new BizRuntimeException(this.getErrorCode(), this.getI18nKey(), this.getErrorMsg());
    }

    /**
     * 1、根据异常枚举构建接口返回对象
     *
     * @param args 额外参数
     * @return 接口返回对象
     */
    public <T> ApiRestResponse<T> asResponse(Object... args) {
        NestedMessageSource messageSource = SpringContextUtils.getContext().getApplicationContext().getBean(NestedMessageSource.class);
        return this.asResponse(messageSource, args);
    }

    /**
     * 1、根据异常枚举构建接口返回对象
     *
     * @param messageSource I18N 国际化资源对象
     * @param args          额外参数
     * @return 接口返回对象
     */
    public <T> ApiRestResponse<T> asResponse(NestedMessageSource messageSource, Object... args) {
        this.errorMsg = messageSource.getMessage(this.getI18nKey(), args, this.getErrorMsg(), LocaleContextHolder.getLocale());
        return ApiRestResponse.of(this);
    }

    public String getI18nKey() {
        return i18nKey;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    @Override
    public int getCode() {
        return errorCode;
    }

    @Override
    public String getReason() {
        return errorMsg;
    }

    @Override
    public String getStatus() {
        return Constants.RT_FAIL;
    }

}
