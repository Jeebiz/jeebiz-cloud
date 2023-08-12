package io.hiwepy.cloud.sms.core.bo;

import io.hiwepy.cloud.sms.core.enums.SmsChannel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Map;

/**
 * @author wandl
 */
@ApiModel(value = "SendSmsBO", description = "发送短信BO")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SendSmsBO {

    /**
     * 调试模式（务必保证当前对象不能直接作为Controller参数对象，以免发生生产事故）
     */
    @ApiModelProperty(value = "调试模式")
    private boolean debug;
    /**
     * 是否忽略短信发送的前置检查
     */
    @ApiModelProperty(value = "是否忽略短信发送的前置检查")
    private boolean ignoreCheck;
    /**
     * 应用ID
     */
    @ApiModelProperty(required = true, value = "应用ID")
    private String appId;
    /**
     * 应用渠道编码
     */
    @ApiModelProperty(required = true, value = "应用渠道编码")
    private String appChannel;
    /**
     * 应用版本号
     */
    @ApiModelProperty(required = true, value = "应用版本号")
    private String appVer;
    /**
     * 发送短信来源IP地址
     */
    @ApiModelProperty(required = true, value = "发送短信来IP地址")
    private String ipAddress;
    /**
     * 短信流水号
     */
    @ApiModelProperty(hidden = true, value = "短信流水号")
    private String smsNo;
    /**
     * 唤起发送短信的用户uid
     */
    @ApiModelProperty(required = true, value = "唤起发送短信的用户uid")
    private String userId;
    /**
     * 唤起发送短信的用户code
     */
    @ApiModelProperty(required = true, value = "唤起发送短信的用户code")
    private String userCode;
    /**
     * 业务ID
     */
    @ApiModelProperty(required = true, value = "业务ID")
    private String bizId;
    /**
     * 国家编码，例：86
     */
    @ApiModelProperty(required = true, value = "国家编码，例：86")
    private Integer countryCode;
    /**
     * 短信接收号码
     */
    @ApiModelProperty(required = true, value = "短信接收号码")
    private String phone;
    /**
     * 短信验证码
     */
    @ApiModelProperty(required = true, value = "短信验证码")
    private String vcode;
    /**
     * 短信验证码有效期（单位：秒）
     */
    private Long expire;
    /**
     * 短信接收内容
     */
    @ApiModelProperty(required = true, value = "短信接收内容")
    private String content;
    /**
     * 发送短信渠道
     */
    @ApiModelProperty(required = true, value = "发送短信渠道")
    private SmsChannel channel;
    /**
     * 短信模板参数
     */
    @ApiModelProperty(required = true, value = "短信模板参数")
    private Map<String, Object> templateParams;

}
