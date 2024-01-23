package com.ssafy.umzip.domain.member.service;

import com.ssafy.umzip.domain.member.repository.MemberRepository;
import com.ssafy.umzip.global.common.StatusCode;
import com.ssafy.umzip.global.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    public void createUser() {
        memberRepository.findById(5L)
                .orElseThrow(() -> new BaseException(StatusCode.ALREADY_EXIST));

    }
}
