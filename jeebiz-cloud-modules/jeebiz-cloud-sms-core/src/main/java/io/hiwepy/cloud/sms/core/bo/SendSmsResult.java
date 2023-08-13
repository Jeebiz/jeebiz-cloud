package io.hiwepy.cloud.sms.core.bo;

import io.hiwepy.cloud.sms.core.enums.SmsChannel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author wandl
 */
@ApiModel(value = "SendSmsResult", description = "支付结果")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SendSmsResult {

    /**
     * 唤起发送短信的用户uid
     */
    @ApiModelProperty(value = "唤起发送短信的用户uid")
    private String userId;
    /**
     * 业务ID
     */
    @ApiModelProperty(value = "业务ID")
    private String bizId;
    /**
     * 请求ID
     */
    @ApiModelProperty(value = "请求ID")
    private String requestId;
    /**
     * 发送短信渠道
     */
    @ApiModelProperty(value = "发送短信渠道")
    private SmsChannel channel;
    /**
     * 短信发送状态（ 0：发送失败、1：发送成功）
     */
    @ApiModelProperty(value = "短信发送状态（ 0：发送失败、1：发送成功）")
    private Integer status;
    /**
     * 发送短信内容
     */
    @ApiModelProperty(value = "发送短信内容")
    private String content;
    /**
     * 发送短信内容
     */
    @ApiModelProperty(value = "发送短信内容")
    private String message;

}

