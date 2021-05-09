package com.github.marceloleite2604.websocketserver.configuration;

import com.github.marceloleite2604.websocketserver.ChatHandler;
import com.github.marceloleite2604.websocketserver.properties.WebSocketProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class ChatWebSocketConfigurer implements WebSocketConfigurer {

    private final WebSocketProperties websocketProperties;

    private final ChatHandler chatHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatHandler, websocketProperties.getPaths()
                .toArray(String[]::new))
                .setAllowedOrigins(websocketProperties.getAllowedOrigins()
                        .toArray(String[]::new))
                .addInterceptors(new HttpSessionHandshakeInterceptor());
    }
}
