package io.hiwepy.cloud.sample;

import io.hiwepy.boot.autoconfigure.EnableExtrasConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.client.RestTemplate;

import io.micrometer.core.instrument.MeterRegistry;

@EnableCaching(proxyTargetClass = true)
@EnableExtrasConfiguration
@EnableFeignClients(basePackages = "io.hiwepy.cloud.sample.setup.feign")
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@SpringBootApplication
public class JeebizWebMvcApplication implements CommandLineRunner {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
    @Bean
    public MeterRegistryCustomizer<MeterRegistry> configurer(
            @Value("${spring.application.name}") String applicationName) {
        return (registry) -> registry.config().commonTags("application", applicationName);
    }
	
    public static void main(String[] args) {
        SpringApplication.run(JeebizWebMvcApplication.class, args);
    }
    
	@Override
	public void run(String... args) throws Exception {
		System.err.println("Spring Cloud Application（Jeebiz-Cloud-WebMvc-Sample） Started !");
	}
    
}