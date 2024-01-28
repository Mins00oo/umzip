package com.ssafy.umzip.domain.member.controller;

import com.ssafy.umzip.domain.member.dto.MemberCreateRequestDto;
import com.ssafy.umzip.domain.member.dto.MemberResponseDto;
import com.ssafy.umzip.domain.member.service.MemberService;
import com.ssafy.umzip.global.common.BaseResponse;
import com.ssafy.umzip.global.common.StatusCode;
import com.ssafy.umzip.global.util.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 일반 사용자 회원가입시 요청을 처리하는 메서드
     */
    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody MemberCreateRequestDto requestDto) {

        memberService.createMember(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(StatusCode.SUCCESS));
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<Object> retrieveUser(@PathVariable Long memberId, HttpServletRequest request) {
        Long requestId = jwtTokenProvider.getId(request);
        MemberResponseDto responseDto = memberService.retrieveMember(memberId, requestId);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(responseDto));
    }

}
