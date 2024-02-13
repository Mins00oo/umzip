package com.ssafy.umzip.domain.chat.controller;

import com.ssafy.umzip.domain.chat.dto.ChatMessageRequestDto;
import com.ssafy.umzip.domain.chat.dto.ChatMessageResponseDto;
import com.ssafy.umzip.domain.chat.service.ChatRoomService;
import com.ssafy.umzip.domain.chat.service.ChatService;
import com.ssafy.umzip.global.util.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate template;
    private final JwtTokenProvider jwtTokenProvider;
    private final ChatRoomService chatRoomService;
    private final ChatService chatService;

    /**
     * 업체, 도움 글 사용자간의 채팅
     */
    @MessageMapping("/chat/{chatRoomId}")
    public void send(@Payload ChatMessageRequestDto message, @DestinationVariable Long chatRoomId,
                     @Header("Authorization") String authToken) {
        Long requestId = jwtTokenProvider.getIdByToken(authToken);
        String role = jwtTokenProvider.getRoleByToken(authToken);
        ChatMessageResponseDto response;

        if (message.getType().equals("LEAVE")) {
            chatRoomService.leaveChatRoom(chatRoomId, requestId);
            message.setContent("상대방이 나갔습니다.");
        }
        response = chatService.saveMessage(message, chatRoomId, requestId, role);

        template.convertAndSend("/topic/chatroom/" + chatRoomId, response);
    }

}