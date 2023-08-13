package io.hiwepy.cloud.message.email.setup.event;

import hitool.mail.JVMNetProxy;
import hitool.mail.authc.ProxyAuthenticator;
import io.hiwepy.cloud.message.email.setup.Constants;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationListener;

import java.net.Authenticator;
import java.util.Map;
import java.util.Properties;

/**
 * 系统参数更新事件监听，实现与业务代码解耦；比如开启关闭网络代理
 */
public class MailtoxSettingsUpdateEventListener implements ApplicationListener<MailtoxSettingsUpdateEvent> {

    @Override
    public void onApplicationEvent(MailtoxSettingsUpdateEvent event) {

        //第一步：获取绑定的参数数据
        Map<String, String> tempMap = event.getBind();

        //第二步：调用参数变化要调用的逻辑代码

        //网络代理开关
        //使用网络代理收发邮件
        String net_proxy_enable = tempMap.get(Constants.PREFIX + JVMNetProxy.JVM_NET_PROXY_ENABLE);
        if (BooleanUtils.toBoolean(net_proxy_enable)) {

            //网络代理类型 nomoral,http,https,socks
            String net_proxy_type = tempMap.get(Constants.PREFIX + JVMNetProxy.JVM_NET_PROXY_TYPE);
            String net_proxy_user = tempMap.get(Constants.PREFIX + JVMNetProxy.JVM_NET_PROXY_USER);
            String net_proxy_password = tempMap.get(Constants.PREFIX + JVMNetProxy.JVM_NET_PROXY_PASSWORD);
            // 网络代理服主机IP地址
            String net_proxy_host = tempMap.get(Constants.PREFIX + JVMNetProxy.JVM_PROXY_HOST);
            // 网络代理服主机的端口
            String net_proxy_port = tempMap.get(Constants.PREFIX + JVMNetProxy.JVM_PROXY_PORT);
            // 设置不需要通过代理服务器访问的主机，可以使用*通配符，多个地址用|分隔
            String net_proxy_non_hosts = tempMap.get(Constants.PREFIX + JVMNetProxy.JVM_PROXY_NON_HOSTS);

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

}