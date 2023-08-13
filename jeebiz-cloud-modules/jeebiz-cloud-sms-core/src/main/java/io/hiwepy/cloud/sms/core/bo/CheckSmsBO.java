package io.hiwepy.cloud.sms.core.bo;

import io.hiwepy.cloud.sms.core.enums.SmsChannel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@ApiModel(value = "CheckSmsBO", description = "验证短信BO")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class CheckSmsBO {

    /**
     * 调试模式（务必保证当前对象不能直接作为Controller参数对象，以免发生生产事故）
     */
    @ApiModelProperty(name = "debug", required = true, value = "支付调试")
    private boolean debug;
    /**
     * 应用ID
     */
    @ApiModelProperty(name = "appId", required = true, value = "应用ID")
    private String appId;
    /**
     * 应用渠道编码
     */
    @ApiModelProperty(name = "appChannel", required = true, value = "应用渠道编码")
    private String appChannel;
    /**
     * 应用版本号
     */
    @ApiModelProperty(name = "appVer", required = true, value = "应用版本号")
    private String appVer;
    /**
     * 发送短信来源IP地址
     */
    @ApiModelProperty(name = "ipAddress", required = true, value = "发送短信来IP地址")
    private String ipAddress;
    /**
     * 唤起发送短信的用户uid
     */
    @ApiModelProperty(name = "userId", required = true, value = "唤起发送短信的用户uid")
    private String userId;
    /**
     * 业务ID
     */
    @ApiModelProperty(name = "bizId", required = true, value = "业务ID")
    private String bizId;

    /**
     * 国家编码，例：86
     */
    @ApiModelProperty(name = "countryCode", required = true, value = "国家编码，例：86")
    private Integer countryCode;

    /**
     * 短信接收号码
     */
    @ApiModelProperty(name = "phone", required = true, value = "短信接收号码")
    private String phone;

    /**
     * 发送短信内容
     */
    @ApiModelProperty(name = "content", required = true, value = "发送短信内容")
    private String content;
    /**
     * 发送短信渠道
     */
    @ApiModelProperty(name = "channel", required = true, value = "支付渠道")
    private SmsChannel channel;

}
