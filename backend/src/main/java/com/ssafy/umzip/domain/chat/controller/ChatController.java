package com.ssafy.umzip.domain.chat.controller;

import com.ssafy.umzip.domain.chat.dto.ChatMessageRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate template;

    @MessageMapping("/chat/{chatRoomId}")
    public void send(@Payload ChatMessageRequestDto message, @DestinationVariable Long chatRoomId) {
        if (message.getType().equals("LEAVE")) {
            message.setContent("상대방이 나갔습니다.");
        }

        log.info(String.valueOf(message));
        template.convertAndSend("/topic/chatroom/" + chatRoomId, message.getContent());
    }
}
