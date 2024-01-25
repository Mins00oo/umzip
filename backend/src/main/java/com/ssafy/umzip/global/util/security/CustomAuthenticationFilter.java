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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final MemberDetailService memberDetailService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        final Authentication authentication;
        final MemberLoginRequestDto userLoginDto;
        try {
            userLoginDto = new ObjectMapper().readValue(request.getInputStream(), MemberLoginRequestDto.class);
            MemberDetailsImpl memberDetails = (MemberDetailsImpl) memberDetailService.loadUserByUsername(userLoginDto.getEmail());
            authentication = new UsernamePasswordAuthenticationToken(memberDetails, "",  memberDetails.getAuthorities());
        } catch (IOException e) {
            throw new BadCredentialsException("bad");
        }
        return authentication;
    }
}
