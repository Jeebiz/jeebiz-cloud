package io.hiwepy.cloud.sms.flow.dao.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.hiwepy.boot.api.dao.entities.BaseEntity;
import io.hiwepy.cloud.sms.core.enums.SmsChannel;
import lombok.*;

@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "t_sms_flow")
public class SmsFlowEntity extends BaseEntity<SmsFlowEntity> {

    /**
     * 主键，自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 客户端应用ID
     */
    @TableField(value = "app_id")
    private String appId;
    /**
     * 客户端应用渠道
     */
    @TableField(value = "app_channel")
    private String appChannel;
    /**
     * 客户端版本
     */
    @TableField(value = "app_version")
    private String appVer;
    /**
     * 发起发送短信的用户id
     */
    @TableField(value = "user_id")
    private String userId;
    /**
     * 发起发送短信的用户code
     */
    @TableField(value = "user_code")
    private String userCode;
    /**
     * 请求ID
     */
    @TableField(value = "request_id")
    private String requestId;
    /**
     * 业务ID
     */
    @TableField(value = "biz_id")
    private String bizId;
    /**
     * 请求来源IP地址
     */
    @TableField(value = "source_ip")
    private String ipAddress;
    /**
     * 请求来源国：根据支付请求IP地址解析
     */
    @TableField(value = "source_country")
    private String country;
    /**
     * 短信流水号
     */
    @TableField(value = "sms_no")
    private String smsNo;
    /**
     * 发送渠道
     */
    @TableField(value = "sms_channel")
    private SmsChannel channel;
    /**
     * 发送短信内容
     */
    @TableField(value = "sms_content")
    private String content;
    /**
     * 发送流水描述
     */
    @TableField(value = "sms_desc")
    private String flowDesc;

}
