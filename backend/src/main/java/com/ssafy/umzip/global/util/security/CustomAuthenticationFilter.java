package com.ssafy.umzip.global.util.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.umzip.domain.member.dto.MemberLoginRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        final UsernamePasswordAuthenticationToken authRequest;
        final MemberLoginRequestDto userLoginDto;
        try {
            // 사용자 요청 정보로 UserPasswordAuthenticationToken 발급
            userLoginDto = new ObjectMapper().readValue(request.getInputStream(), MemberLoginRequestDto.class);
            authRequest = new UsernamePasswordAuthenticationToken(userLoginDto.getEmail(), userLoginDto.getPwd());
        } catch (IOException e) {
            throw new BadCredentialsException("bad");
        }
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
