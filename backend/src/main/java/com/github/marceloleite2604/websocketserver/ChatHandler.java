package com.github.marceloleite2604.websocketserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.marceloleite2604.websocketserver.model.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChatHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage inputTextMessage) throws IOException {

        try {
            var inputMessage = objectMapper.readValue(inputTextMessage.getPayload(), Message.class);
            log.info("Received message from \"{}\": {}", inputMessage.from(), inputMessage.content());
            var outputContent = String.format("Hello %s, welcome to the chat.", inputMessage.from());
            var outputMessage = new Message("Server", outputContent);
            var payload = objectMapper.writeValueAsString(outputMessage);
            session.sendMessage(new TextMessage(payload));
        } catch (JsonProcessingException exception) {
            log.error("Received a message, but it could not be processed.", exception);
        }
    }

}
