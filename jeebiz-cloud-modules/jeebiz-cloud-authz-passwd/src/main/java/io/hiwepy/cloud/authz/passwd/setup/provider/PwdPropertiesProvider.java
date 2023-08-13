package io.hiwepy.cloud.authz.passwd.setup.provider;

import java.util.Properties;

public interface PwdPropertiesProvider {

    public Properties props();

    public void setProps(Properties props);

}
