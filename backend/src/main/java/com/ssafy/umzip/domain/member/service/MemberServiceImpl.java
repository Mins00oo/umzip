package com.ssafy.umzip.domain.member.service;

import com.ssafy.umzip.domain.member.dto.MemberCreateRequestDto;
import com.ssafy.umzip.domain.member.entity.Member;
import com.ssafy.umzip.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;

    @Override
    public Member createMember(MemberCreateRequestDto requestDto) {
        Member member = MemberCreateRequestDto.toEntity(
                requestDto,
                encoder.encode(requestDto.getPassword()),
                1000
        );
        memberRepository.save(member);

        return member;
    }

}
