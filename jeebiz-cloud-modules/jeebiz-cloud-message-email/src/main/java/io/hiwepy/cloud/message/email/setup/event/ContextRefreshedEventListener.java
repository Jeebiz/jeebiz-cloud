package io.hiwepy.cloud.message.email.setup.event;

import hitool.mail.JVMNetProxy;
import hitool.mail.authc.ProxyAuthenticator;
import io.hiwepy.boot.api.dao.entities.PairModel;
import io.hiwepy.boot.api.utils.CollectionUtils;
import io.hiwepy.cloud.message.email.service.IMailtoxSettingsService;
import io.hiwepy.cloud.message.email.setup.Constants;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.net.Authenticator;
import java.util.*;

/**
 * 监听容器启动状态，更新当前应用的网络代理设置，解决每次重启都需要设置问题
 */
public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {

    protected final IMailtoxSettingsService mailtoxSettingsService;
    ;
    protected Timer timer = new Timer(); // 1. 创建Timer实例，关联线程不能是daemon(守护/后台)线程

    public ContextRefreshedEventListener(IMailtoxSettingsService mailtoxSettingsService) {
        super();
        this.mailtoxSettingsService = mailtoxSettingsService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        timer.purge();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {

                List<PairModel> pairList = getMailtoxSettingsService().getPairValues("");
                Map<String, Object> pairMap = new HashMap<>();
                if (!CollectionUtils.isEmpty(pairList)) {
                    for (PairModel pairModel : pairList) {
                        pairMap.put(pairModel.getKey(), StringUtils.trimToNull(pairModel.getValue()));
                    }
                }

                // 使用网络代理收发邮件
                String net_proxy_enable = MapUtils.getString(pairMap, Constants.PREFIX + JVMNetProxy.JVM_NET_PROXY_ENABLE);
                if (BooleanUtils.toBoolean(net_proxy_enable)) {

                    // 网络代理类型 nomoral,http,https,socks
                    String net_proxy_type = MapUtils.getString(pairMap, Constants.PREFIX + JVMNetProxy.JVM_NET_PROXY_TYPE);
                    String net_proxy_user = MapUtils.getString(pairMap, Constants.PREFIX + JVMNetProxy.JVM_NET_PROXY_USER);
                    String net_proxy_password = MapUtils.getString(pairMap, Constants.PREFIX + JVMNetProxy.JVM_NET_PROXY_PASSWORD);
                    // 网络代理服主机IP地址
                    String net_proxy_host = MapUtils.getString(pairMap, Constants.PREFIX + JVMNetProxy.JVM_PROXY_HOST);
                    // 网络代理服主机的端口
                    String net_proxy_port = MapUtils.getString(pairMap, Constants.PREFIX + JVMNetProxy.JVM_PROXY_PORT);
                    // 设置不需要通过代理服务器访问的主机，可以使用*通配符，多个地址用|分隔
                    String net_proxy_non_hosts = MapUtils.getString(pairMap, Constants.PREFIX + JVMNetProxy.JVM_PROXY_NON_HOSTS);

                    /*------------------------ JVM 网络代理设置 ------------------------*/

                    Properties prop = System.getProperties();

                    prop.setProperty(JVMNetProxy.JVM_NET_PROXY_ENABLE, "true");

                    if ("nomoral".equalsIgnoreCase(net_proxy_type)) {

                        prop.setProperty(JVMNetProxy.JVM_PROXY_HOST, net_proxy_host);
                        prop.setProperty(JVMNetProxy.JVM_PROXY_PORT, net_proxy_port);
                        prop.setProperty(JVMNetProxy.JVM_PROXY_NON_HOSTS, net_proxy_non_hosts);

                    }
                    // Http网络代理
                    else if ("http".equalsIgnoreCase(net_proxy_type)) {

                        prop.setProperty(JVMNetProxy.JVM_HTTP_PROXY_HOST, net_proxy_host);
                        prop.setProperty(JVMNetProxy.JVM_HTTP_PROXY_PORT, net_proxy_port);
                        prop.setProperty(JVMNetProxy.JVM_HTTP_PROXY_NON_HOSTS, net_proxy_non_hosts);
                        prop.setProperty(JVMNetProxy.JVM_HTTP_PROXY_USER, "");
                        prop.setProperty(JVMNetProxy.JVM_HTTP_PROXY_PASSWORD, "");

                        if (StringUtils.isNotEmpty(net_proxy_user)) {
                            // 设置登陆到代理服务器的用户名和密码
                            Authenticator.setDefault(new ProxyAuthenticator(net_proxy_user, net_proxy_password));
                        }

                    }
                    // Https网络代理
                    else if ("https".equalsIgnoreCase(net_proxy_type)) {

                        prop.setProperty(JVMNetProxy.JVM_HTTPS_PROXY_PROXYHOST, net_proxy_host);
                        prop.setProperty(JVMNetProxy.JVM_HTTPS_PROXY_PROXYHOST, net_proxy_port);
                    }
                    // Socks网络代理服务器的地址与端口
                    else if ("socks".equalsIgnoreCase(net_proxy_type)) {

                        prop.setProperty(JVMNetProxy.JVM_SOCKS_PROXY_HOST, net_proxy_host);
                        prop.setProperty(JVMNetProxy.JVM_SOCKS_PROXY_PORT, net_proxy_port);
                    }


                } else {

                    /*------------------------ 取消 JVM 网络代理设置 ------------------------*/
                    Properties prop = System.getProperties();
                    prop.remove(JVMNetProxy.JVM_NET_PROXY_ENABLE);
                    prop.remove(JVMNetProxy.JVM_PROXY_HOST);
                    prop.remove(JVMNetProxy.JVM_PROXY_PORT);
                    prop.remove(JVMNetProxy.JVM_PROXY_NON_HOSTS);
                    prop.remove(JVMNetProxy.JVM_HTTP_PROXY_HOST);
                    prop.remove(JVMNetProxy.JVM_HTTP_PROXY_PORT);
                    prop.remove(JVMNetProxy.JVM_HTTP_PROXY_USER);
                    prop.remove(JVMNetProxy.JVM_HTTP_PROXY_PASSWORD);
                    prop.remove(JVMNetProxy.JVM_HTTP_PROXY_NON_HOSTS);
                    prop.remove(JVMNetProxy.JVM_HTTPS_PROXY_PROXYHOST);
                    prop.remove(JVMNetProxy.JVM_HTTPS_PROXY_PROXYHOST);
                    prop.remove(JVMNetProxy.JVM_SOCKS_PROXY_HOST);
                    prop.remove(JVMNetProxy.JVM_SOCKS_PROXY_PORT);

                }

            }

        }, 5000);

    }

    public IMailtoxSettingsService getMailtoxSettingsService() {
        return mailtoxSettingsService;
    }

}
