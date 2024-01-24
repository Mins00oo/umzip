package com.ssafy.umzip.global.util.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.umzip.domain.member.entity.Member;
import com.ssafy.umzip.domain.member.repository.MemberRepository;
import com.ssafy.umzip.global.common.BaseResponse;
import com.ssafy.umzip.global.common.Role;
import com.ssafy.umzip.global.util.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Log4j2
public class AuthSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper mapper;
    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Member member = memberRepository.findByEmail(String.valueOf(authentication.getPrincipal()))
                .orElseThrow(() -> new BadCredentialsException("회원 x"));

        String token = jwtTokenProvider.createAccessToken(member.getEmail(), Role.USER, member.getId());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        JwtTokenProvider.setAccessTokenInHeader(token, response);

        Map<String, String> tokenDto = new HashMap<>();

        tokenDto.put("accessToken", token);

        BaseResponse<Map<String, String>> baseResponse = new BaseResponse<>(tokenDto);

        mapper.writeValue(response.getWriter(), baseResponse);
    }
}
