package com.ssafy.umzip.domain.company.service;

import com.ssafy.umzip.domain.company.dto.CompanyCreateRequestDto;
import com.ssafy.umzip.domain.company.dto.CompanyResponseDto;
import com.ssafy.umzip.domain.company.entity.Company;
import com.ssafy.umzip.domain.company.repository.CompanyRepository;
import com.ssafy.umzip.domain.member.dto.MemberCreateRequestDto;
import com.ssafy.umzip.domain.member.entity.Member;
import com.ssafy.umzip.domain.member.service.MemberService;
import com.ssafy.umzip.domain.reviewreceiver.repository.ReviewReceiverRepository;
import com.ssafy.umzip.domain.reviewreceiver.service.ReviewReceiverService;
import com.ssafy.umzip.global.common.Role;
import com.ssafy.umzip.global.common.StatusCode;
import com.ssafy.umzip.global.exception.BaseException;
import com.ssafy.umzip.global.util.s3.S3Service;
import com.ssafy.umzip.global.util.s3.S3UploadDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final MemberService memberService;
    private final S3Service s3Service;
    private final ReviewReceiverRepository reviewReceiverRepository;
    private final ReviewReceiverService reviewReceiverService;

    @Transactional
    @Override
    public void createCompany(MemberCreateRequestDto memberCreateRequestDto,
                              List<CompanyCreateRequestDto> companyCreateRequestDtoList,
                              MultipartFile deliveryCertificate,
                              MultipartFile deliveryImg,
                              MultipartFile cleanImg) {

        Member member = memberService.createMember(memberCreateRequestDto);

        S3UploadDto deliverFileDto = uploadFile(deliveryCertificate, "deliveryCertificate");
        S3UploadDto deliverImgDto = uploadFile(deliveryImg, "deliveryCompanyImg");
        S3UploadDto cleanImgDto = uploadFile(cleanImg, "cleanCompanyImg");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (CompanyCreateRequestDto companyCreateRequestDto : companyCreateRequestDtoList) {
            Role companyRole = getRoleForCompanyType(companyCreateRequestDto.getCompanyType());

            S3UploadDto imgDto = companyRole.equals(Role.DELIVER) ? deliverImgDto : cleanImgDto;

            if (companyRole != Role.DELIVER) {
                deliverFileDto = null;
            }

            Company company = CompanyCreateRequestDto.toEntity(
                    companyCreateRequestDto,
                    LocalDate.parse(companyCreateRequestDto.getStartDate(), formatter).atStartOfDay(),
                    companyRole,
                    deliverFileDto,
                    imgDto,
                    member
            );

            companyRepository.save(company);
        }
    }

    @Override
    public CompanyResponseDto retrieveCompany(Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new BaseException(StatusCode.NOT_EXIST_COMPANY));

        List<String> tagList = reviewReceiverRepository.findTopTagsByMemberId(company.getMember().getId(), 3);
        String formattedScore = reviewReceiverService.receiverReviewScore(company.getMember().getId());

        return CompanyResponseDto.fromEntity(company, formattedScore, tagList);
    }

    private Role getRoleForCompanyType(int companyType) {
        if (companyType == 1) {
            return Role.DELIVER;
        }
        return Role.CLEAN;
    }

    private S3UploadDto uploadFile(MultipartFile file, String fileNamePrefix) {
        if (file != null && !file.isEmpty()) {
            return s3Service.upload(file, "umzip-service", fileNamePrefix);
        }
        return null;
    }
}
