package io.hiwepy.cloud.base.guard.dao.entities;

import io.hiwepy.boot.api.dao.entities.PaginationEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@SuppressWarnings("serial")
@Alias(value = "AntisamyModel")
@Getter
@Setter
@ToString
public class AntisamyModel extends PaginationEntity<AntisamyModel> {
    /**
     * 消息通知对象ID
     */
    private String id;
    /**
     * 消息通知对象ID
     */
    private String userid;

    /**
     * 服务通知信息标题
     */
    private String title;
    /**
     * 服务通知信息内容
     */
    private String detail;
    /**
     * 服务通知信息阅读状态：0:未阅读|1:已阅读
     */
    private String status;
    /**
     * 服务通知信息送达时间
     */
    private String timestamp;

}
