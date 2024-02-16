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
import java.util.ArrayList;
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

        Member member = memberRepository.findByEmail(String.valueOf(authentication.getPrincipal()))
                .orElseThrow(
                        () -> new BaseException(StatusCode.NOT_VALID_EMAIL));
        List<Company> companyList = companyRepository.findAllByMemberId(member.getId());
        MemberTokenDto tokenDto;

        if (companyList.isEmpty()) {
            tokenDto = createTokenDto(member, null, new ArrayList<>(), 1, member.getId());
        } else {
            List<Role> roleList = new ArrayList<>();
            Company company = (companyList.size() == 2)
                    ? companyList.stream()
                    .filter(list -> list.getRole() == Role.DELIVER)
                    .findAny()
                    .orElseThrow(() -> new BaseException(StatusCode.COMPANY_ROLE_NOT_MATCH))
                    : companyList.get(0);

            if (companyList.size() == 2) {
                roleList.add(Role.DELIVER);
                roleList.add(Role.CLEAN);
            } else {
                roleList.add(company.getRole());
            }

            tokenDto = createTokenDto(member, company, roleList, -1, company.getId());
        }

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        JwtTokenProvider.setAccessTokenInHeader(tokenDto.getAccessToken(), response);
        JwtTokenProvider.setRefreshTokenInHeader(tokenDto.getRefreshToken(), response);

        BaseResponse<MemberTokenDto> tokenResponse = new BaseResponse<>(tokenDto);

        mapper.writeValue(response.getWriter(), tokenResponse);
    }

    // 공통 코드를 메소드로 분리
    private MemberTokenDto createTokenDto(Member member, Company company, List<Role> roleList, int who, Long id) {
        MemberTokenDto tokenDto = (company == null)
                ? jwtTokenProvider.generateMemberToken(member)
                : jwtTokenProvider.generateCompanyToken(company);

        tokenDto.setName((company == null) ? member.getName() : company.getName());
        tokenDto.setProfileImage((company == null) ? member.getImageUrl() : company.getImageUrl());
        tokenDto.setWho(who);
        tokenDto.setRoleList(roleList);
        tokenDto.setId(id);

        return tokenDto;
    }
}
