package com.ssafy.umzip.domain.member.service;

import com.ssafy.umzip.domain.member.dto.MemberCreateRequestDto;
import com.ssafy.umzip.domain.member.entity.Member;
import org.springframework.web.multipart.MultipartFile;

public interface MemberService {
    Member createMember(MemberCreateRequestDto requestDto);
}
