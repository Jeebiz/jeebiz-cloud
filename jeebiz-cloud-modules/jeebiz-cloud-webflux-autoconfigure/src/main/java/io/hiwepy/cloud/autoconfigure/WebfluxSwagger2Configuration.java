/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.autoconfigure;

import io.springfox.spring.boot.Swagger2WebFluxProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@ConditionalOnProperty(prefix = Swagger2WebFluxProperties.PREFIX, value = "enabled", havingValue = "true")
public class WebfluxSwagger2Configuration {

    @Bean
    public RouterFunction<ServerResponse> docRouter(
            @Value("classpath:/META-INF/resources/doc.html") final Resource indexHtml) {
        return RouterFunctions.route(RequestPredicates.GET("/"),
                request -> ServerResponse.ok().contentType(MediaType.TEXT_HTML).bodyValue(indexHtml));
    }

}
