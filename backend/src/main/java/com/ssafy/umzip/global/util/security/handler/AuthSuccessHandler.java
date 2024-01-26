package com.ssafy.umzip.global.util.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.umzip.domain.company.entity.Company;
import com.ssafy.umzip.domain.company.repository.CompanyRepository;
import com.ssafy.umzip.domain.member.entity.Member;
import com.ssafy.umzip.domain.member.repository.MemberRepository;
import com.ssafy.umzip.global.common.BaseResponse;
import com.ssafy.umzip.global.common.Role;
import com.ssafy.umzip.global.common.StatusCode;
import com.ssafy.umzip.global.exception.BaseException;
import com.ssafy.umzip.global.util.jwt.JwtTokenProvider;
import com.ssafy.umzip.global.util.jwt.MemberTokenDto;
import com.ssafy.umzip.global.util.security.MemberDetailsImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Log4j2
public class AuthSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper mapper;
    private final MemberRepository memberRepository;
    private final CompanyRepository companyRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Object principal = authentication.getPrincipal();
        MemberDetailsImpl memberDetails = (MemberDetailsImpl) principal;
        Member member = memberRepository.findByEmail(memberDetails.getUsername())
                .orElseThrow(
                        () -> new BaseException(StatusCode.NOT_VALID_EMAIL));
        List<Company> companyList = companyRepository.findAllByMemberId(member.getId());
        MemberTokenDto tokenDto;

        if (companyList.isEmpty()) {
            tokenDto = jwtTokenProvider.generateMemberToken(member);
        } else {
            if (companyList.size() == 2) {
                // 무조건 용달
                tokenDto = jwtTokenProvider.generateCompanyToken(companyList.stream()
                        .filter(company -> company.getRole() == Role.DELIVER)
                        .findAny()
                        .orElseThrow(() -> new BaseException(StatusCode.COMPANY_ROLE_NOT_MATCH)));
            } else
                tokenDto = jwtTokenProvider.generateCompanyToken(companyList.get(0));
        }

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        JwtTokenProvider.setAccessTokenInHeader(tokenDto.getAccessToken(), response);
        JwtTokenProvider.setRefreshTokenInHeader(tokenDto.getRefreshToken(), response);

        BaseResponse<MemberTokenDto> tokenResponse = new BaseResponse<>(tokenDto);

        log.info(authentication);
        mapper.writeValue(response.getWriter(), tokenResponse);
    }
}
