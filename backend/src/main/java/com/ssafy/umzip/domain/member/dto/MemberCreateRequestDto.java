package com.ssafy.umzip.domain.member.dto;

import com.ssafy.umzip.domain.member.entity.Member;
import com.ssafy.umzip.global.util.s3.S3UploadDto;
import lombok.Getter;

@Getter
public class MemberCreateRequestDto {
    private String name;

    private String email;

    private String phone;

    private String password;

    private String address;

    private int sigungu;

    private String addressDetail;

    public static Member toEntity(
            MemberCreateRequestDto requestDto,
            String encodedPassword,
            int point,
            S3UploadDto s3UploadDto
    ) {
        return Member.builder()
                .email(requestDto.getEmail())
                .phone(requestDto.getPhone())
                .name(requestDto.getName())
                .point(point)
                .s3UploadDto(s3UploadDto)
                .address(requestDto.getAddress())
                .addressDetail(requestDto.getAddressDetail())
                .sigungu(requestDto.getSigungu())
                .pwd(encodedPassword)
                .build();
    }
}
