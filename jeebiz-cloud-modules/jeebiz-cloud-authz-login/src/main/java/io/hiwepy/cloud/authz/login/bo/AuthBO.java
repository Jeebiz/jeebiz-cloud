package io.hiwepy.cloud.authz.login.bo;

import io.hiwepy.cloud.authz.login.strategy.AuthChannel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthBO<T> {

    /**
     * 登陆方式
     */
    private AuthChannel channel;
    /**
     * 登录账户id
     */
    private Long accountId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 用户号
     */
    private String userCode;
    /**
     * 身份类型（1：教师、2：学生、3：家长、4：系统用户）
     */
    private Integer identityId;
    /**
     * 所属组织机构ID
     */
    private Long deptId;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 电子邮箱
     */
    private String email;
    /**
     * 性别 1男、2女
     */
    private Integer gender;
    /**
     * 生日
     */
    private Long birthday;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 用户年龄
     */
    private Integer age;
    /**
     * 用户位置：常驻国家
     */
    private String country;
    /**
     * 用户位置：常驻省份
     */
    private String province;
    /**
     * 用户位置：常驻城市
     */
    private String city;
    /**
     * 用户位置：常驻区域
     */
    private String area;
    /**
     * 用户位置：常驻地经度
     */
    private Double longitude;
    /**
     * 用户位置：常驻地纬度
     */
    private Double latitude;
    /**
     * 用户类型
     */
    private Integer type;
    /**
     * 用户角色
     */
    private Long roleId;
    /**
     * 账号
     */
    private String account;
    /**
     * 密码
     */
    private String password;
    /**
     * 加密后的密码
     */
    private String encodePassword;
    /**
     * 应用ID
     */
    private String appId;
    /**
     * 应用渠道编码
     */
    private String appChannel;
    /**
     * 应用版本号
     */
    private String appVer;
    /**
     * 请求来源IP地址
     */
    private String ipAddress;
    /**
     * 请求来源平台
     */
    private String paltform;
    /**
     * 设备唯一标识
     */
    private String devId;
    /**
     * 设备名称
     */
    private String device;
    /**
     * 国际码
     */
    private String lang;
    /**
     * 登录时间戳
     */
    private Long loginTime;
    /**
     * 用户是否绑定信息
     */
    private Boolean bound = Boolean.FALSE;
    /**
     * 用户是否完善信息
     */
    private Boolean initial = Boolean.FALSE;
    /**
     * 用户是否需要多因子验证
     */
    private Boolean verify = Boolean.FALSE;

    private T param;

}
