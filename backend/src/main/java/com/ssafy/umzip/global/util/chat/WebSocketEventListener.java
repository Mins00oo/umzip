package com.ssafy.umzip.global.util.chat;

import com.ssafy.umzip.domain.chat.entity.ChatMessage;
import com.ssafy.umzip.domain.chat.repository.ChatMessageRepository;
import com.ssafy.umzip.domain.chat.repository.ChatParticipantRepository;
import com.ssafy.umzip.global.util.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketEventListener {
    private final SimpMessagingTemplate messagingTemplate;
    private final JwtTokenProvider jwtTokenProvider;
    private final ChatParticipantRepository chatParticipantRepository;
    private final ChatMessageRepository chatMessageRepository;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        log.info("Received a new web socket connection");
    }

    @Transactional
    @EventListener
    public void handleWebSocketSubscribeListener(SessionSubscribeEvent event) {
        String destination = event.getMessage().getHeaders().get("simpDestination").toString();
        MessageHeaders headers = event.getMessage().getHeaders();
        Map<String, Object> sessionAttributes = (Map<String, Object>) headers.get(SimpMessageHeaderAccessor.SESSION_ATTRIBUTES);

        String accessToken = sessionAttributes.get("accessToken").toString();
        Long memberId = jwtTokenProvider.getIdByAccessToken(accessToken);
        Long chatRoomId;

        if (destination.startsWith("/topic/chatroom/")) {
            chatRoomId = parseChatRoomId(destination);
            List<ChatMessage> messageList = chatMessageRepository.findAllByChatRoomId(chatRoomId);
            String lastMessageId = null;
            if (!messageList.isEmpty()) {
                lastMessageId = messageList.get(messageList.size() - 1).getId();
            }

            chatParticipantRepository.findByChatRoomAndMember(chatRoomId, memberId).updateLastReadMessage(lastMessageId);
        }

        messagingTemplate.convertAndSend("/topic/user/" + accessToken, memberId);

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
