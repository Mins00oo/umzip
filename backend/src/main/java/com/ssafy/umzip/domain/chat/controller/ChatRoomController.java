package com.ssafy.umzip.domain.chat.controller;

import com.ssafy.umzip.domain.chat.dto.ChatDetailResponseDto;
import com.ssafy.umzip.domain.chat.dto.ChatRoomListResponseDto;
import com.ssafy.umzip.domain.chat.entity.ChatRoom;
import com.ssafy.umzip.domain.chat.service.ChatRoomService;
import com.ssafy.umzip.domain.chat.service.ChatService;
import com.ssafy.umzip.global.common.BaseResponse;
import com.ssafy.umzip.global.util.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@Slf4j
public class ChatRoomController {
    private final JwtTokenProvider jwtTokenProvider;
    private final ChatRoomService chatRoomService;
    private final ChatService chatService;

    /**
     * 용달 업체와 사용자 간의 채팅방 생성
     */
    @PostMapping("/delivery/{receiverId}")
    public ResponseEntity<Object> createDeliverChat(HttpServletRequest request, @PathVariable Long receiverId) {
        Long senderId = jwtTokenProvider.getId(request);
        String senderRole = jwtTokenProvider.getRole(request);
        ChatRoom chatRoom = chatRoomService.createChatRoom(senderId, senderRole, receiverId, null, "DELIVER");
        Long chatRoomId = chatRoom.getId();
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(chatRoomId));
    }

    /**
     * 청소 업체와 사용자 간의 채팅방 생성
     */
    @PostMapping("/clean/{receiverId}")
    public ResponseEntity<Object> createCleanChat(HttpServletRequest request, @PathVariable Long receiverId) {
        Long senderId = jwtTokenProvider.getId(request);
        String senderRole = jwtTokenProvider.getRole(request);
        ChatRoom chatRoom = chatRoomService.createChatRoom(senderId, senderRole, receiverId, null, "CLEAN");
        Long chatRoomId = chatRoom.getId();
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(chatRoomId));
    }

    /**
     * 중고거래 사용자 간 채팅
     */
    @PostMapping("/trade/{tradeId}/{receiverId}")
    public ResponseEntity<Object> createTradeChat(HttpServletRequest request, @PathVariable Long tradeId,
                                                  @PathVariable Long receiverId) {
        Long senderId = jwtTokenProvider.getId(request);
        String senderRole = jwtTokenProvider.getRole(request);
        ChatRoom chatRoom = chatRoomService.createChatRoom(senderId, senderRole, receiverId, tradeId, "TRADE");
        Long chatRoomId = chatRoom.getId();
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(chatRoomId));
    }

    /**
     * 사용자와 사용자 간의 채팅방 생성 (도움)
     */
    @PostMapping("/users/{receiverId}")
    public ResponseEntity<Object> createUserChat(HttpServletRequest request, @PathVariable Long receiverId) {
        Long senderId = jwtTokenProvider.getId(request);
        String senderRole = jwtTokenProvider.getRole(request);
        ChatRoom chatRoom = chatRoomService.createChatRoom(senderId, senderRole, receiverId, null, "USER");
        Long chatRoomId = chatRoom.getId();
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(chatRoomId));
    }

    /**
     * 내 채팅방 목록 조회
     */
    @GetMapping("/rooms")
    public ResponseEntity<Object> retrieveChatRoom(HttpServletRequest request) {
        Long memberId = jwtTokenProvider.getId(request);
        String role = jwtTokenProvider.getRole(request);
        List<ChatRoomListResponseDto> response = chatRoomService.retrieveChatRoom(memberId, role);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(response));
    }

    /**
     * 채팅방 상세 조회
     */
    @GetMapping("/rooms/{chatRoomId}")
    public ResponseEntity<Object> retrieveChatRoomMessage(@PathVariable Long chatRoomId, HttpServletRequest request) {
        Long requestId = jwtTokenProvider.getId(request);
        String role = jwtTokenProvider.getRole(request);
        ChatDetailResponseDto response = chatService.retrieveChatRoomMessages(chatRoomId, requestId, role);

        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(response));
    }


}