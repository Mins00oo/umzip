package com.ssafy.umzip.domain.auth.controller;

import com.google.gson.Gson;
import com.ssafy.umzip.domain.auth.dto.*;
import com.ssafy.umzip.domain.auth.service.AuthService;
import com.ssafy.umzip.global.common.BaseResponse;
import com.ssafy.umzip.global.common.StatusCode;
import com.ssafy.umzip.global.util.jwt.JwtTokenProvider;
import com.ssafy.umzip.global.util.jwt.MemberTokenDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    private static String serviceKey;

    @Value("${business.api.key}")
    public void setSecretKey(String serviceKey) {
        AuthController.serviceKey = serviceKey;
    }

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

    @PostMapping("/email")
    public ResponseEntity<Object> authEmail(@RequestBody AuthEmailRequestDto emailRequestDto) {
        authService.authEmail(emailRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(StatusCode.SUCCESS));
    }

    @PostMapping("/business-code")
    public ResponseEntity<Object> authBusinessCode(@RequestBody AuthBusinessRequestDto businessRequestDto) {
        OkHttpClient client = new OkHttpClient().newBuilder().build();

        AuthBusinessRequest authBusinessRequest = businessRequestDto.toAuthBusinessRequest();
        Gson gson = new Gson();
        String json = gson.toJson(authBusinessRequest);

        okhttp3.RequestBody body = okhttp3.RequestBody.create(
                json,
                MediaType.parse("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url("https://api.odcloud.kr/api/nts-businessman/v1/validate?serviceKey=" + serviceKey)
                .method("POST", body)
                .addHeader("Content-type", "application-json")
                .build();

        try {
            Response response = client.newCall(request).execute();

            String responseBody = response.body().string();
            BusinessValidationResponseDto responseDto = gson.
                    fromJson(responseBody, BusinessValidationResponseDto.class);

            if (responseDto.getData().get(0).getValid().equals("02")) {
                return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(StatusCode.NOT_VALID_BUSINESS_NUMBER));
            }
            return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(StatusCode.SUCCESS));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/new/{authNo}")
    public ResponseEntity<Object> changeAuth(@PathVariable Long authNo, HttpServletRequest request) {
        Long companyId = jwtTokenProvider.getId(request);

        MemberTokenDto tokenDto = authService.changeAuth(companyId, authNo);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(tokenDto));
    }

}
