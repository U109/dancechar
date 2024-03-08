package com.litian.dancechar.gateway.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;

import java.util.Optional;

/**
 * Swagger uri 处理
 *
 * @author tojson
 * @date 2021/6/22 09:51
 */
@Component
public class SwaggerUiHandler implements HandlerFunction<ServerResponse> {
    @Autowired(required = false)
    private UiConfiguration uiConfiguration;

    @Override
    public Mono<ServerResponse> handle(ServerRequest request) {
        return ServerResponse.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(
                        Optional.ofNullable(uiConfiguration)
                                .orElse(UiConfigurationBuilder.builder().build())));
    }
}