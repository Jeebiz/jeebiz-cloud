package io.hiwepy.cloud.sms.aliyun.bo;

import io.hiwepy.cloud.sms.core.bo.SendSmsBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@ApiModel(value = "AliyunSendSmsBO", description = "阿里云短信发送BO")
@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
public class AliyunSendSmsBO0 extends SendSmsBO {

    /**
     * 验证码短信模板
     */
    @ApiModelProperty(name = "templateCode", value = "验证码短信模板")
    private String templateCode;
    /**
     * 短信签名索引，对应配置的签名数组元素索引值，默认0
     */
    @ApiModelProperty(name = "signIndx", value = "短信签名索引，对应配置的签名数组元素索引值，默认0")
    private int signIndx;

}
