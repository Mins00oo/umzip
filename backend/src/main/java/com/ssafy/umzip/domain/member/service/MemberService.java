package com.ssafy.umzip.domain.member.service;

import com.ssafy.umzip.domain.member.dto.MemberCreateRequestDto;
import com.ssafy.umzip.domain.member.dto.MemberResponseDto;
import com.ssafy.umzip.domain.member.entity.Member;

public interface MemberService {
    Member createMember(MemberCreateRequestDto requestDto);

    MemberResponseDto retrieveMember(Long id, Long requestId);
}
