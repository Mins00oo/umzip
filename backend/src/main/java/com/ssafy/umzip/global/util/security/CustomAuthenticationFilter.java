package com.ssafy.umzip.global.util.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.umzip.domain.member.dto.MemberLoginRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            MemberLoginRequestDto userLoginDto = new ObjectMapper().readValue(request.getInputStream(), MemberLoginRequestDto.class);

            // 이메일과 비밀번호를 사용하여 UsernamePasswordAuthenticationToken 생성
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userLoginDto.getEmail(),
                    userLoginDto.getPwd()
            );

            // AuthenticationManager를 사용하여 인증 시도
            return this.getAuthenticationManager().authenticate(authenticationToken);
        } catch (IOException e) {
            throw new BadCredentialsException("Authentication service exception", e);
        }
    }
}
