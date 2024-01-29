package com.ssafy.umzip.domain.chat.controller;

import com.ssafy.umzip.domain.chat.dto.ChatMessageRequestDto;
import com.ssafy.umzip.global.common.BaseResponse;
import com.ssafy.umzip.global.common.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ChatController {

    @MessageMapping("/chat-send")
    public ResponseEntity<Object> sendMessage(@Payload ChatMessageRequestDto messageRequestDto) {
      log.info("chat send success" + messageRequestDto);
      return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(StatusCode.SUCCESS));
    }
}
