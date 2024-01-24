package com.ssafy.umzip.domain.company.dto;


import com.ssafy.umzip.domain.company.entity.Company;
import com.ssafy.umzip.domain.member.entity.Member;
import com.ssafy.umzip.global.common.Role;
import com.ssafy.umzip.global.util.s3.S3UploadDto;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CompanyCreateRequestDto {
    private String companyName;

    private int companyType;

    private String businessNumber;

    private String startDate;

    private String introduction;

    private String ceo;

    private String address;

    private String addressDetail;

    private int sigungu;

    public static Company toEntity(
            CompanyCreateRequestDto requestDto,
            LocalDateTime experience,
            Role role,
            S3UploadDto deliverFileDto,
            S3UploadDto imgDto,
            Member member
    ) {
        return Company.builder()
                .name(requestDto.getCompanyName())
                .businessAuthentication(requestDto.getBusinessNumber())
                .introduction(requestDto.getIntroduction())
                .role(role)
                .deliverFileDto(deliverFileDto)
                .experience(experience)
                .imgDto(imgDto)
                .address(requestDto.getAddress())
                .ceo(requestDto.getCeo())
                .addressDetail(requestDto.getAddressDetail())
                .sigungu(requestDto.getSigungu())
                .member(member)
                .build();
    }
}
