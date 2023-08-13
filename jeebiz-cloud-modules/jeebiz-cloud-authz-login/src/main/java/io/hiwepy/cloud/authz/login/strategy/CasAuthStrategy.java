package io.hiwepy.cloud.authz.login.strategy;

import com.google.common.collect.Sets;
import io.hiwepy.cloud.authz.login.bo.AuthBO;
import io.hiwepy.cloud.authz.login.param.LoginByCasParam;
import io.hiwepy.cloud.authz.rbac.dao.entities.UserAccountEntity;
import io.hiwepy.cloud.authz.rbac.dao.entities.UserProfileEntity;
import io.hiwepy.cloud.authz.rbac.dao.entities.UserRoleEntity;
import lombok.extern.slf4j.Slf4j;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.security.boot.biz.userdetails.SecurityPrincipal;
import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 账号密码登录策略
 */
@Slf4j
public class CasAuthStrategy extends AbstractAuthStrategy<LoginByCasParam> {

    @Override
    public AuthChannel getChannel() {
        return AuthChannel.CAS;
    }

    @Override
    public AuthBO<LoginByCasParam> initInfo(Authentication token) throws AuthenticationException {
        CasAssertionAuthenticationToken upToken = (CasAssertionAuthenticationToken) token;
        AuthBO<LoginByCasParam> authBO = AuthBO.<LoginByCasParam>builder()
                .account(upToken.getPrincipal().toString())
                .password(upToken.getCredentials().toString())
                .channel(this.getChannel())
                .build();
        LoginByCasParam param = new LoginByCasParam();
        param.setAssertion(upToken.getAssertion());
        authBO.setParam(param);
        return authBO;
    }

    @Override
    public AuthBO<LoginByCasParam> initInfo(AuthBO<LoginByCasParam> authBO){
        LoginByCasParam param = authBO.getParam();

        Assertion assertion = param.getAssertion();

        /*
         * 获取用户扩展信息 扩展信息由UIA的SSO配置决定 其中，由于用户可能拥有多个角色，岗位，部门
         * 所以角色，岗位，部门需要被转换为List<Map<String,String>类型
         */
        Map<String, Object> map = assertion.getPrincipal().getAttributes();

        /*
         * 角色集合 集合内元素为Map<String,String>类型 Map中详细信息为： key：ROLECNNAME;value:角色中文名称 key:ROLEIDENTIFY;value:角色代码
         */
        //List<Map<String, String>> role = parseStringToList((String) map.get("comsys_role"));
        /*
         * 岗位集合 集合内元素为Map<String,String>类型 Map中详细信息为： key：POSTNAME;value:岗位中文名称 key:
         * POSTIDENTIFY;value:岗位代码
         */
        //List<Map<String, String>> post = parseStringToList((String) map.get("comsys_post"));
        /*
         * 部门集合 集合内元素为Map<String,String>类型 Map中详细信息为： key：DEPARTMENTNAME;value:部门中文名称
         * key: DEPARTMENTIDENTIFY;value:部门代码
         */
        //List<Map<String, String>> department = parseStringToList((String) map.get("comsys_department"));
        // 用户id
        String userId = (String) map.get("userId");
        // 登录类型
        String loginType = (String) map.get("type");
        //姓名
        String nickname = (String) map.get("nickname");
        // 账户名
        String username = (String) map.get("username");
        //手机号
        String mobile = (String) map.get("mobile");
        // 账户id
        String accountId = (String) map.get("accountId");
        param.setUserId(userId);
        param.setLoginType(loginType);
        param.setUsername(username);
        param.setAccountId(accountId);
        param.setNickName(nickname);
        param.setMobile(mobile);
        authBO.setParam(param);
        return authBO;
    }

    @Override
    public SecurityPrincipal login(AuthBO<LoginByCasParam> authBO) throws AuthenticationException {
        SecurityPrincipal principal = new SecurityPrincipal(authBO.getAccount(),
                "NO_PASSWORD", true,
                true, true,true, Sets.newHashSet());
        UserAccountEntity account = getUserAccountService().getAccountByName(AuthChannel.PASSWORD.getKey(), authBO.getAccount());
        UserProfileEntity profile = getUserProfileService().getById(account.getUserId());
        List<UserRoleEntity> userRoles = getUserRoleService().getUserRoleListByUserId(account.getUserId());
        principal.setUid(account.getUserId().toString());
        principal.setUuid(account.getId().toString());
        if(!CollectionUtils.isEmpty(userRoles)){

            Set<GrantedAuthority> grantedAuthorities = Sets.newHashSet();
            Set<String> perms = Sets.newHashSet();

            Optional<UserRoleEntity> roleFirst = CollectionUtils.isEmpty(userRoles) ? Optional.empty()
                    : Objects.nonNull(authBO.getRoleId())
                    ? userRoles.stream().filter(role -> role.getId().equals(authBO.getRoleId()))
                    .findFirst()
                    : userRoles.stream().findFirst();
            try {

                /*
                // 有设置角色：构造用户权限
                if (roleFirst.isPresent()) {

                    UserRoleEntity role = roleFirst.get();

                    // 角色必须是ROLE_开头，可以在数据库中设置
                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(
                            StringUtils.startsWithIgnoreCase(role.getKey(), "ROLE_") ? role.getKey()
                                    : "ROLE_" + role.getKey());

                    grantedAuthorities.add(grantedAuthority);

                    // 用户权限标记集合
                    perms.addAll(getRolePermsService().getPermissions(role.getId()));
                    for (String perm : perms) {
                        GrantedAuthority authority = new SimpleGrantedAuthority(perm);
                        grantedAuthorities.add(authority);
                    }

                }*/

                // principal.setUserid(model.getUserid());
                // principal.setAlias(model.getAlias());
                principal.setPerms(perms);

                // 有设置角色：构造角色信息
                if (roleFirst.isPresent()) {
                    /*
                    List<JwtPayload.RolePair> roles = CollectionUtils.isEmpty(userRoles) ? Lists.newArrayList()
                            : userRoles.stream().map(role -> {
                        return new JwtPayload.RolePair(role.getId(), role.getKey(), role.getName());
                    }).collect(Collectors.toList());

                    principal.setRoles(roles);*/
                    //principal.setRcode(roleFirst.get().getKey());
                    principal.setRid(roleFirst.get().getId().toString());

                }
                // principal.setInitial(model.isInitial());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        principal.setProfile(profile.toMap());
        return principal;
    }

}
