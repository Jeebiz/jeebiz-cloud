/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.facex.dao.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.hiwepy.boot.api.dao.entities.PaginationEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;

@Alias("AuthzFaceEntity")
@Accessors(chain = true)
@Getter
@Setter
@SuppressWarnings("serial")
@ToString
@TableName(value = "t_user_face")
@EqualsAndHashCode(callSuper = true)
public class AuthzFaceEntity extends PaginationEntity<AuthzFaceEntity> {

    /**
     * 人脸识别数据ID
     */
    @TableId(value = "face_id", type = IdType.AUTO)
    private String id;
    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    protected String userId;
    /**
     * 用户别名（昵称）
     */
    @TableField(exist = false)
    private String nickname;
    /**
     * 人脸识别数据分组ID
     */
    @TableField(value = "repo_id")
    private String repoId;
    /**
     * 分组名称
     */
    @TableField(exist = false)
    private String repoName;
    /**
     * 人脸识别图片base64编码后的图片数据（图片的base64编码不包含图片头的，如data:image/jpg;base64,）
     */
    @TableField(value = "face_base64")
    private String face;
    /**
     * 人脸的类型：(LIVE:表示生活照;通常为手机、相机拍摄的人像图片、或从网络获取的人像图片等|IDCARD:表示身份证芯片照;二代身份证内置芯片中的人像照片|WATERMARK:表示带水印证件照;一般为带水印的小图，如公安网小图|CERT:表示证件照片;如拍摄的身份证、工卡、护照、学生证等证件图片);
     * 默认LIVE
     */
    @TableField(value = "face_type")
    private String type;
    /**
     * 人脸图片的唯一标识
     */
    @TableField(value = "face_token")
    private String token;


}
