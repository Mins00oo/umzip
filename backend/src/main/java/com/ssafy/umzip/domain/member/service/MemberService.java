package com.ssafy.umzip.domain.member.service;

import com.ssafy.umzip.domain.member.dto.MemberCreateRequestDto;
import org.springframework.web.multipart.MultipartFile;

public interface MemberService {
    void createMember(MemberCreateRequestDto requestDto, MultipartFile multipartFile);
}
