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
@ApiModel(value = "SstSendSmsResult", description = "师生通短信发送结果")
@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SstSendSmsResult extends SendSmsResult {

    /**
     * 返回码
     */
    @ApiModelProperty(value = "返回码")
    private Integer code;

    /**
     * 提示信息
     */
    @ApiModelProperty(value = "提示信息")
    private String msg;

    /**
     * 数据信息
     */
    @ApiModelProperty(value = "数据信息")
    private String data;

}
