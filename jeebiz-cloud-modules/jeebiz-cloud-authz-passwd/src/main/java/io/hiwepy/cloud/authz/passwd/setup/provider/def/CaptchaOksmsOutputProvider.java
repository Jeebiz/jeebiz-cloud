/*package io.hiwepy.cloud.authz.passwd.setup.provider.def;

import java.util.Properties;

import com.github.hiwepy.oksms.core.OksmsPayload;
import com.github.hiwepy.oksms.spring.boot.OksmsTemplate;

import io.hiwepy.cloud.authz.passwd.setup.Constants;
import io.hiwepy.cloud.authz.passwd.setup.dto.PwdRetakeDto;
import io.hiwepy.cloud.authz.passwd.setup.provider.CaptchaOutputProvider;
import io.hiwepy.cloud.authz.passwd.setup.provider.PwdPropertiesProvider;
import io.hiwepy.cloud.authz.passwd.setup.strategy.PwdStrategy;
import io.hiwepy.cloud.authz.passwd.utils.PatternFormatUtils;

 * 短信发送服务提供者
 
public class CaptchaOksmsOutputProvider implements CaptchaOutputProvider {

	protected final OksmsTemplate oksmsTemplate;

	protected PwdPropertiesProvider propsProvider = null;
	
	public CaptchaOksmsOutputProvider(OksmsTemplate oksmsTemplate) {
		super();
		this.oksmsTemplate = oksmsTemplate;
	}

	@Override
	public String name() {
		return this.getClass().getName();
	}
	
	@Override
	public boolean output(PwdRetakeDto dto)  throws Exception {
		
		Properties props = getPropsProvider().props();
		
		//检查参数配置
		if(!props.containsKey(Constants.PWD_SMS_CONTENT)){
			throw new IllegalArgumentException("密码找回参数: " + Constants.PWD_SMS_CONTENT + " not found. ");
		}
		
		// 构造消息体
		OksmsPayload payload = new OksmsPayload();
		
		payload.setUuid(dto.getUuid());
		
		// 接收人的手机号码
		String sendTo = (String) dto.getData().get(PwdStrategy.PWD_RETAKE_BY_PHONE);
		payload.setMobile(sendTo);
		
		// 短信的内容体
        String content = PatternFormatUtils.format(props.getProperty(Constants.PWD_SMS_CONTENT), dto.getData());
		payload.setContent(content);
		
		getOksmsTemplate().send(payload);
		
		return true;
		
	}

	public PwdPropertiesProvider getPropsProvider() {
		return propsProvider;
	}

	public void setPropsProvider(PwdPropertiesProvider propsProvider) {
		this.propsProvider = propsProvider;
	}

	public OksmsTemplate getOksmsTemplate() {
		return oksmsTemplate;
	}
	
}*/
