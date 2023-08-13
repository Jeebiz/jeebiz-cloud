package io.hiwepy.cloud.sample.setup.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import feign.Request;
import feign.Retryer;

@Configuration
public class FeignClientsConfiguration {

	@Value("${host.feign.request.connectTimeoutMillis}")
    int connectTimeoutMillis;
    @Value("${host.feign.request.readTimeoutMillis}")
    int readTimeoutMillis;

    @Bean
    @Scope("prototype")
    public Request.Options feginOption() {
        // 读取与连接超时，有一个超时了都会降级
        Request.Options option = new Request.Options(connectTimeoutMillis, readTimeoutMillis);
        return option;
    }
    
    @Bean
    public Retryer feignRetryer() {
    	// return new Retryer.Default(period, maxPeriod, maxAttempts)
        return new Retryer.Default();
    }
	
}
