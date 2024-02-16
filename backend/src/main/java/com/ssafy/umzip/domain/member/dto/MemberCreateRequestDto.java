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
            int point
    ) {
        return Member.builder()
                .email(requestDto.getEmail())
                .phone(requestDto.getPhone())
                .name(requestDto.getName())
                .point(point)
                .imageUrl("https://umzip-service.s3.ap-northeast-2.amazonaws.com/deliveryCompanyImg/f1fdf575-abb2-475e-95f9-e9710c54c6cabackenda3b976a0-d918-4f61-a29b-5257e80a49c0.jpg")
                .address(requestDto.getAddress())
                .addressDetail(requestDto.getAddressDetail())
                .sigungu(requestDto.getSigungu())
                .pwd(encodedPassword)
                .build();
    }
}
