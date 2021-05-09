package com.github.marceloleite2604.websocketserver.properties;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@ConfigurationProperties("websocket")
@Data
@RequiredArgsConstructor
public class WebSocketProperties {

    private final Set<String> paths;

    private final Set<String> allowedOrigins;
}
