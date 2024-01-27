package com.ssafy.umzip.domain.member.service;

import com.ssafy.umzip.domain.member.dto.MemberCreateRequestDto;
import com.ssafy.umzip.domain.member.dto.MemberResponseDto;
import com.ssafy.umzip.domain.member.entity.Member;
import com.ssafy.umzip.domain.member.repository.MemberRepository;
import com.ssafy.umzip.domain.reviewreceiver.repository.ReviewReceiverRepository;
import com.ssafy.umzip.domain.reviewreceiver.service.ReviewReceiverService;
import com.ssafy.umzip.global.common.StatusCode;
import com.ssafy.umzip.global.exception.BaseException;
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
    private final ReviewReceiverRepository reviewReceiverRepository;
    private final ReviewReceiverService reviewReceiverService;

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

    @Override
    public MemberResponseDto retrieveMember(Long id, Long requestId) {
        boolean isMe = id.equals(requestId);

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new BaseException(StatusCode.NOT_VALID_MEMBER_PK));

        String formattedAverageScore = reviewReceiverService.receiverReviewScore(member.getId());

        return MemberResponseDto.fromEntity(member, isMe, String.valueOf(formattedAverageScore));
    }

}
