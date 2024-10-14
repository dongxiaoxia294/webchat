package com.giraffe.webchat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
@Component
public class WebSocketConfig {

    @Bean
    //注入 ServerEndpointExporter bean对象自动注册使用@ServerEndpoint
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
