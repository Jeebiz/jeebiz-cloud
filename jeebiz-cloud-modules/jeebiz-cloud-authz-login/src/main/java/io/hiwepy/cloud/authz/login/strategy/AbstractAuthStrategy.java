package io.hiwepy.cloud.authz.login.strategy;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import hitool.core.lang3.RandomString;
import io.hiwepy.boot.api.sequence.Sequence;
import io.hiwepy.cloud.authz.login.bo.AuthBO;
import io.hiwepy.cloud.authz.login.exception.BizExceptionCode;
import io.hiwepy.cloud.authz.login.exception.ThirdUserNotFoundException;
import io.hiwepy.cloud.authz.login.param.RegisterParam;
import io.hiwepy.cloud.authz.login.service.IAuthService;
import io.hiwepy.cloud.authz.rbac.dao.entities.UserAccountEntity;
import io.hiwepy.cloud.authz.rbac.dao.entities.UserProfileEntity;
import io.hiwepy.cloud.authz.rbac.service.*;
import io.hiwepy.cloud.base.redis.BizRedisKey;
import io.hiwepy.cloud.base.redis.BizRedisKeyConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisOperationTemplate;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.boot.biz.userdetails.SecurityPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;
import java.util.Set;

@Slf4j
public abstract class AbstractAuthStrategy<T> implements AuthStrategy<T> {

    protected RandomString randomString = new RandomString();

    private static final String SUFFIX = "}";
    @Value(value = "${register.switch:false}")
    private boolean registerSwitch;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IAuthService authService;
    @Autowired
    private IUserAccountService userAccountService;
    @Autowired
    private IUserProfileService userProfileService;
    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IRolePermsService rolePermsService;
    @Autowired
    private Sequence sequence;
    @Autowired
    private RedisOperationTemplate redisOperation;

    @Override
    public AuthBO<T> initInfo(AuthBO<T> authBO) throws AuthenticationException {
        return authBO;
    }

    /**
     * 注册账户，根据需要创建用户
     *
     * @param authBO
     * @param registerParam
     * @return Long
     * @throws AuthenticationException
     */
    protected Long register(AuthBO<T> authBO, RegisterParam registerParam) throws AuthenticationException {
        // 通过【注册参数】向账号表添加新账号
		/*RegisterUserVo vo = new RegisterUserVo();
		vo.setUsername(authBO.getAccount());
		vo.setPassword(authBO.getPassword());
		vo.setLoginType(getChannel().getKey());*/
        UserAccountEntity account = null;//accountService.registerAccount(vo);
        Long accountId = account.getId();
        /**
         * 封装传递参数
         */
        authBO.setAccountId(accountId);
        if (registerParam.getUserId() != null) {
            authBO.setUserId(Long.valueOf(registerParam.getUserId()));
        }
        authBO.setEncodePassword(account.getPassword());
        afterReg(authBO, accountId);
        return accountId;
    }

    /**
     * 是否可以绑定账号
     *
     * @param account
     * @return
     */
    protected Boolean checkBindByAccount(AuthBO<T> account) throws AuthenticationException {
        return false;
    }

    /**
     * 把获取到的账号信息转化为注册所要用到的参数
     *
     * @param authBO<T>
     * @return
     */
    protected RegisterParam getRegisterParam(AuthBO<T> authBO) {
        RegisterParam registerParam = new RegisterParam();
        registerParam.setChannel(getChannel());
        registerParam.setLang(authBO.getLang());
        return registerParam;
    }

    protected void setProfileWithParam(AuthBO<T> authBO, UserProfileEntity profile) {
        profile.setUserId(authBO.getUserId());
        profile.setNickname(authBO.getNickname());
        profile.setPhone(authBO.getPhone());
    }

    /**
     * 是否需要注册
     *
     * @return
     */
    protected Boolean needReg(AuthBO<T> authBO) {
        return this.isRegisterSwitch();
    }

    @Override
    public final SecurityPrincipal login(Authentication token) throws AuthenticationException {
        // 1、获取登录用的账号
        AuthBO<T> authBO = initInfo(token);
        // 2、执行登录
        return this.login(authBO);
    }

    /**
     * 登陆
     *
     * @param authBO
     * @return
     * @throws AuthenticationException
     */
    @Override
    public SecurityPrincipal login(AuthBO<T> authBO) throws AuthenticationException {
        // 1、初始化
        authBO = initInfo(authBO);
        // 1、查询是否是已存在的账号(通过登陆方式+登陆账户)
        UserAccountEntity userAccountEntity = getUserAccountService().getAccountByName(this.getChannel().getKey(), authBO.getAccount());
        boolean isFirst = false;
        Long accountId = null;
        if (Objects.isNull(userAccountEntity)) {
            // 2、首次登录，没账号 需要注册账号则注册则自动注册账号
            if (needReg(authBO)) {
                // 2.1、获取注册所需参数
                RegisterParam registerParam = getRegisterParam(authBO);
                // 2.2、加锁
                String key = BizRedisKey.USER_LOCK_REG.getKey(registerParam.getAccount());
                String value = String.valueOf(System.currentTimeMillis());
                boolean lock = redisOperation.setNx(key, value, BizRedisKeyConstant.FIVE_SECONDS);
                if (!lock) {
                    throw new LockedException("");
                }
                // 2.3、注册
                accountId = register(authBO, registerParam);
                authBO.setAccountId(accountId);
                redisOperation.unlock(key, value);
                isFirst = true;
            } else {
                // 如果账号表没有账号，并且账号不被自动注册，则抛出异常
                if (AuthChannel.PASSWORD.equals(authBO.getChannel())) {
                    //throw new BadCredentialsException("Authentication Failed. Username or Password not valid.");
                    throw new ThirdUserNotFoundException(BizExceptionCode.USER_OR_PASSWORD_IS_NOT_EXIST.getErrorMsg());
                } else {
                    throw new ThirdUserNotFoundException(BizExceptionCode.THIRD_SCAN_NOT_EXISTS.getErrorMsg());
                }
            }
        } else {
            // 存在账号
            authBO.setEncodePassword(userAccountEntity.getPassword());
            // todo 如果是密码方式登陆，则要判断登陆的密码是否相符
            if (AuthChannel.PASSWORD.equals(authBO.getChannel()) &&
                    (!passwordEncoder.matches(authBO.getPassword(), userAccountEntity.getPassword())
                            && !authBO.getPassword().equals(userAccountEntity.getPassword()))) {
                throw new ThirdUserNotFoundException(BizExceptionCode.USER_OR_PASSWORD_IS_NOT_EXIST.getErrorMsg());
            }
            accountId = userAccountEntity.getId();
            // 将账号表的用户ID和账号ID注入AuthBO中
            authBO.setAccountId(accountId);
            authBO.setUserId(userAccountEntity.getUserId());
        }
        checkAndBind(authBO);
        // 登陆
        SecurityPrincipal login = getPrincipal(authBO);
//		login.setInitial(isFirst); // 首次注册登陆
        authBO.setLoginTime(System.currentTimeMillis());

        if (Objects.nonNull(userAccountEntity) && Objects.nonNull(userAccountEntity.getUserId())) {
            afterLogin(authBO, userAccountEntity.getUserId());
        }
        return login;
    }

    /**
     * 加密
     *
     * @param prefixEncodedPassword
     * @return
     */
    private String extractEncodedPassword(String prefixEncodedPassword) {
        int start = prefixEncodedPassword.indexOf(SUFFIX);
        return prefixEncodedPassword.substring(start + 1);
    }

    @Override
    public SecurityPrincipal runAs(AuthBO<T> authBO) throws AuthenticationException {
        // 1、获取登录用的账号
        authBO = initInfo(authBO);
        // 2、查询已存在的账号
        UserAccountEntity userAccountEntity = getUserAccountService().getAccountById(authBO.getAccountId());
        authBO.setAccountId(userAccountEntity.getId());
        authBO.setUserId(userAccountEntity.getUserId());
        authBO.setAccount(userAccountEntity.getAccount());
        authBO.setEncodePassword(userAccountEntity.getPassword());
        // 3、执行登录
        SecurityPrincipal login = getPrincipal(authBO);
        //login.setInitial(false); // 首次注册登陆
        authBO.setLoginTime(System.currentTimeMillis());
        // 4、登录完成后操作
        if (Objects.nonNull(userAccountEntity) && Objects.nonNull(userAccountEntity.getUserId())) {
            afterLogin(authBO, userAccountEntity.getUserId());
        }
        return login;
    }

    @Override
    public final SecurityPrincipal getPrincipal(AuthBO<T> authBO) throws AuthenticationException {
        log.info("{} >> Login AuthBo : {}", authBO.getAccountId(), JSONObject.toJSONString(authBO));
        Set<GrantedAuthority> grantedAuthorities = Sets.newHashSet();
        Set<String> perms = Sets.newHashSet();
        SecurityPrincipal principal = null;
        boolean verify = false;
        Long userId = 0L;
        UserProfileEntity profile = new UserProfileEntity();

        if (Objects.nonNull(authBO.getUserId()) && authBO.getUserId() != 0) {
            userId = authBO.getUserId();
            profile = getUserProfileService().getEntityByIdentity(authBO.getUserId(), authBO.getIdentityId());
            //verify = roleService.isNeedMultipulVerify(userId);
        } else {
            //profile.setStatus(Constants.USER_STATUS_OPEN);
            setProfileWithParam(authBO, profile);
        }
        //profile.setAccountId(authBO.getAccountId().toString());
		/*if(Constants.USER_STATUS_CLOSE.equals(profile.getStatus())){
			throw new ThirdUserNotFoundException(BizExceptionCode.USER_IS_LOCKED.getErrorMsg());
		}*/
        // 用户状态（0:禁用|1:可用）
        principal = new SecurityPrincipal(authBO.getAccount(),
                authBO.getEncodePassword(), /*Constants.USER_STATUS_OPEN.equals(profile.getStatus())*/ true,
                true, true, true, grantedAuthorities);


        principal.setUid(userId.toString());
        principal.setBound(authBO.getBound());
        principal.setInitial(authBO.getInitial());
        principal.setVerify(verify);
        principal.setUuid(authBO.getAccountId().toString());
        principal.setProfile(profile.toMap());
        return principal;
    }

    @Override
    public void afterLogin(AuthBO<T> object, Long userId) {
        object.setAccountId(userId);
        authService.afterLogin(object);
    }

    @Override
    public void checkAndBind(AuthBO<T> authBO) throws AuthenticationException {
    }

    @Override
    public void afterReg(AuthBO<T> authBO, Long userId) throws AuthenticationException {
        authBO.setAccountId(userId);
    }

    public boolean isRegisterSwitch() {
        return registerSwitch;
    }


    public IAuthService getAuthService() {
        return authService;
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public IUserAccountService getUserAccountService() {
        return userAccountService;
    }

    public IUserProfileService getUserProfileService() {
        return userProfileService;
    }

    public IUserRoleService getUserRoleService() {
        return userRoleService;
    }

    public RedisOperationTemplate getRedisOperation() {
        return redisOperation;
    }

    public IRoleService getRoleService() {
        return roleService;
    }

    public IRolePermsService getRolePermsService() {
        return rolePermsService;
    }

    public Sequence getSequence() {
        return sequence;
    }


}
