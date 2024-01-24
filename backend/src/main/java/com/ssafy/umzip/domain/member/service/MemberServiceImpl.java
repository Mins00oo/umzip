package com.ssafy.umzip.domain.member.service;

import com.ssafy.umzip.domain.member.dto.MemberCreateRequestDto;
import com.ssafy.umzip.domain.member.entity.Member;
import com.ssafy.umzip.domain.member.repository.MemberRepository;
import com.ssafy.umzip.global.common.StatusCode;
import com.ssafy.umzip.global.exception.BaseException;
import com.ssafy.umzip.global.util.s3.S3UploadDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final S3Service s3Service;
    private final BCryptPasswordEncoder encoder;

    @Override
    public void createMember(MemberCreateRequestDto requestDto, MultipartFile multipartFile) {
        if (checkDuplicateMember(requestDto.getEmail())) {
            throw new BaseException(StatusCode.ALREADY_EXIST_MEMBER);
        }

        S3UploadDto s3UploadDto = null;
        if (!multipartFile.isEmpty()) {
            s3UploadDto = s3Service.upload(multipartFile, "umzip-service", "user");
        }

        Member member = MemberCreateRequestDto.toEntity(
                requestDto,
                encoder.encode(requestDto.getPassword()),
                1000,
                s3UploadDto
        );
        memberRepository.save(member);
    }

    public boolean checkDuplicateMember(String email) {
        return memberRepository.existsByEmail(email);
    }
}
