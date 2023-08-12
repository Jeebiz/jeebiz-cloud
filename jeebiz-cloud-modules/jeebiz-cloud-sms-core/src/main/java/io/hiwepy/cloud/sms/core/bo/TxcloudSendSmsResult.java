package io.hiwepy.cloud.sms.core.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author wandl
 */
@ApiModel(value = "TxcloudSendSmsResult", description = "腾讯云短信发送结果")
@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TxcloudSendSmsResult extends SendSmsResult {

    /**
     * 发送短信结果编码
     */
    @ApiModelProperty(name = "content", value = "发送短信结果编码")
    private String code;

}
