package com.ssafy.umzip.global.util.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketEventListener{
    private final SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        log.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketSubscribeListener(SessionSubscribeEvent event) {
        String destination = event.getMessage().getHeaders().get("simpDestination").toString();
        Long chatRoomId = parseChatRoomId(destination);
        String sessionId = event.getMessage().getHeaders().get("simpSessionId").toString();

        // 여기서 사용자의 마지막 읽은 메시지 ID를 갱신하는 등의 처리를 수행할 수 있습니다.
        // 예: chatRoomService.updateLastReadMessageId(chatRoomId, sessionId);

        System.out.println("New subscription: " + sessionId + ", room: " + chatRoomId);
    }

    private Long parseChatRoomId(String destination) {
        String[] parts = destination.split("/");
        String chatRoomId = parts[parts.length - 1];
        return Long.valueOf(chatRoomId);
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        log.info("web socket disconnect");
    }
}
