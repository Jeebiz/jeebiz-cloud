/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.autoconfigure;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import feign.Request;
import feign.Retryer;
import feign.codec.Decoder;
import hitool.core.lang3.time.DateFormats;
import io.hiwepy.cloud.autoconfigure.feign.FeignAuthorizationInterceptor;
import io.hiwepy.cloud.autoconfigure.feign.FeignFallbackHandler;
import io.hiwepy.cloud.autoconfigure.feign.FeignHystrixConcurrencyStrategy;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.converter.*;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
public class FeignClientsExtConfiguration {

    @Value("${feign.request.connectTimeoutMillis:2000}")
    int connectTimeoutMillis;
    @Value("${feign.request.readTimeoutMillis:180000}")
    int readTimeoutMillis;

    @Bean
    public Decoder feignDecoder(ObjectProvider<ObjectMapper> objectMapperProvider) {

        ObjectMapper objectMapper = objectMapperProvider.getIfAvailable(() -> {
            return Jackson2ObjectMapperBuilder.json()
                    .simpleDateFormat(DateFormats.DATE_LONGFORMAT).failOnEmptyBeans(false)
                    .failOnUnknownProperties(false)
                    .featuresToEnable(MapperFeature.USE_GETTERS_AS_SETTERS, MapperFeature.ALLOW_FINAL_FIELDS_AS_MUTATORS)
                    .build();
        });

        List<HttpMessageConverter<?>> converters = Lists.newArrayList();

        converters.add(new MappingJackson2HttpMessageConverter(objectMapper));
        converters.add(new ByteArrayHttpMessageConverter());
        converters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        converters.add(new ResourceHttpMessageConverter());
        converters.add(new ResourceRegionHttpMessageConverter());
        try {
            converters.add(new SourceHttpMessageConverter<>());
        } catch (Throwable ex) {
            // Ignore when no TransformerFactory implementation is available...
        }
        converters.add(new AllEncompassingFormHttpMessageConverter());

        ObjectFactory<HttpMessageConverters> objectFactory = () -> new HttpMessageConverters(converters);
        return new ResponseEntityDecoder(new SpringDecoder(objectFactory));
    }

    @Bean
    @Scope("prototype")
    public Request.Options feginOption() {
        // 读取与连接超时，有一个超时了都会降级
        return new Request.Options(connectTimeoutMillis, TimeUnit.MILLISECONDS, readTimeoutMillis,
                TimeUnit.MILLISECONDS, false);
    }

    /**
     * 打印请求日志
     *
     * @return
     */
    @Bean
    public feign.Logger.Level multipartLoggerLevel() {
        return feign.Logger.Level.FULL;
    }

    @Bean
    public Retryer feignRetryer() {
        // return new Retryer.Default(period, maxPeriod, maxAttempts)
        return new Retryer.Default();
    }

    @Bean
    public FeignAuthorizationInterceptor feignAuthorizationInterceptor() {
        return new FeignAuthorizationInterceptor();
    }

    @Bean
    public FeignHystrixConcurrencyStrategy feignHystrixConcurrencyStrategy() {
        return new FeignHystrixConcurrencyStrategy();
    }

    @Bean
    public FeignFallbackHandler feginFallbackHandler() {
        return new FeignFallbackHandler() {
        };
    }

}
