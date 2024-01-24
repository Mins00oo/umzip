package com.ssafy.umzip.domain.auth.controller;

import com.ssafy.umzip.domain.auth.dto.AuthCodeRequestDto;
import com.ssafy.umzip.domain.auth.service.AuthService;
import com.ssafy.umzip.global.common.BaseResponse;
import com.ssafy.umzip.global.common.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    /**
     * 요청 휴대폰 번호로 인증번호를 전송하는 메서드
     */
    @PostMapping("/phone")
    public ResponseEntity<Object> sendCode(@RequestBody AuthCodeRequestDto codeRequestDto) {
        authService.sendCode(codeRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(StatusCode.SUCCESS));
    }

    /**
     * 전송된 인증번호와 입력번호가 일치한지 확인하는 메서드
     */
    @PostMapping("/code")
    public ResponseEntity<Object> authCode(@RequestBody AuthCodeRequestDto codeRequestDto) {
        authService.authCode(codeRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(StatusCode.SUCCESS));
    }
}
