package io.hiwepy.cloud.authz.passwd.dao;

import io.hiwepy.boot.api.dao.BaseMapper;
import io.hiwepy.boot.api.dao.entities.BaseMap;
import io.hiwepy.boot.api.dao.entities.PairModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface IPwdRetakeAccountDao extends BaseMapper<PairModel> {

    public Map<String, String> getAccountStatus(@Param(value = "username") String username);

    /**
     * 通过页面绑定的参数查询用户信息
     */
    public BaseMap getAccount(Map<String, Object> map);

    /**
     * 修改用户密码
     *
     * @param map
     * @return
     */
    public int resetPwd(Map<String, Object> map);


}
