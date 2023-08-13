package io.hiwepy.cloud.api.dto;

import lombok.Data;

@Data
public class JpushRecordDTO {

    /**
     * 用户最近一次登录的客户端应用ID
     */
    private String appId;
    /**
     * 消息通知接收人ID（用户ID）
     */
    private Long uid;
    /**
     * 消息通知内容（可能包含变量）
     */
    private String content;

    public JpushRecordDTO() {
        super();
    }

    public JpushRecordDTO(String appId, Long uid, String content) {
        super();
        this.appId = appId;
        this.uid = uid;
        this.content = content;
    }

}
