package io.hiwepy.cloud.sms.core.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * @author wandl
 */
@ApiModel(value = "SstSendSmsBO", description = "师生通短信发送BO")
@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SstSendSmsBO extends SendSmsBO {

    /**
     * 学校代码白名单
     */
    @ApiModelProperty(value = "学校代码白名单")
    private List<String> whiteList;

    /**
     * 学校代码
     */
    @ApiModelProperty(value = "学校代码")
    private String schoolCode;

    /**
     * 用户代码集合
     */
    @ApiModelProperty(value = "用户代码集合")
    private List<String> userCodeList;

    /**
     * 通知点击跳转地址
     */
    @ApiModelProperty(value = "通知点击跳转地址")
    private String url;

}
