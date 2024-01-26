package com.ssafy.umzip.global.util.security;

import com.ssafy.umzip.domain.member.entity.Member;
import com.ssafy.umzip.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationProviderImpl implements AuthenticationProvider {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberDetailService memberDetailService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;

        String username = token.getName();
        String password = String.valueOf(token.getCredentials());

        MemberDetailsImpl memberDetails;

        Member member = memberRepository.findByEmail(username)
                .orElseThrow(() -> new BadCredentialsException("이메일 틀림"));

        memberDetails = (MemberDetailsImpl) memberDetailService.loadUserByUsername(username);


        if (!bCryptPasswordEncoder.matches(password, member.getPwd())) {
            throw new BadCredentialsException("비번 틀림");
        }

        return new UsernamePasswordAuthenticationToken(memberDetails.getUsername(), "", memberDetails.getAuthorities());

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
