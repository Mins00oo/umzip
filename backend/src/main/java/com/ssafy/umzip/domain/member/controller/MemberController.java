package com.ssafy.umzip.domain.member.controller;

import com.ssafy.umzip.domain.member.dto.MemberCreateRequestDto;
import com.ssafy.umzip.domain.member.service.MemberService;
import com.ssafy.umzip.global.common.BaseResponse;
import com.ssafy.umzip.global.common.StatusCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;

    /**
     * 일반 사용자 회원가입시 요청을 처리하는 메서드
     */
    @PostMapping
    public ResponseEntity<Object> createUser(@RequestPart MemberCreateRequestDto requestDto,
                                             @RequestPart(value = "memberProfileImg") MultipartFile multipartFile) {

        memberService.createMember(requestDto, multipartFile);
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(StatusCode.SUCCESS));
    }
}
