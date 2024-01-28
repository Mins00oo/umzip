package com.ssafy.umzip.domain.company.service;

import com.ssafy.umzip.domain.company.dto.CompanyCreateRequestDto;
import com.ssafy.umzip.domain.company.dto.CompanyResponseDto;
import com.ssafy.umzip.domain.member.dto.MemberCreateRequestDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CompanyService {
    void createCompany(MemberCreateRequestDto memberCreateRequestDto,
                       List<CompanyCreateRequestDto> companyCreateRequestDtoList,
                       MultipartFile deliveryCertificate, MultipartFile deliveryImg, MultipartFile cleanImg);

    CompanyResponseDto retrieveCompany(Long companyId);
}
