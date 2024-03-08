package com.litian.dancechar.gateway.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import reactor.core.publisher.Flux;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.charset.StandardCharsets;

/**
 * 请求日志装饰类
 * @author  leo
 * @date 2020/7/5 11:34
 */
@Slf4j
public class RequestLoggingDecorator extends ServerHttpRequestDecorator {

    public RequestLoggingDecorator(ServerHttpRequest delegate) {
        super(delegate);
    }

    @Override
    public Flux<DataBuffer> getBody() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        return super.getBody().doOnNext(dataBuffer -> {
            try {
                Channels.newChannel(bos).write(dataBuffer.asByteBuffer().asReadOnlyBuffer());
                String body = new String(bos.toByteArray(), StandardCharsets.UTF_8);
                log.info("来自客户端的body信息:{}", body);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            } finally {
                try {
                    bos.close();
                } catch (IOException e) {
                    log.error("close bos exception!"+e.getMessage(), e);
                }
            }
        });
    }
}