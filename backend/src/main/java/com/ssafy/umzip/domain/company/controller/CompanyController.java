package com.ssafy.umzip.domain.company.controller;

import com.ssafy.umzip.domain.company.dto.CompanyCreateRequestDto;
import com.ssafy.umzip.domain.company.dto.CompanyResponseDto;
import com.ssafy.umzip.domain.company.service.CompanyService;
import com.ssafy.umzip.domain.member.dto.MemberCreateRequestDto;
import com.ssafy.umzip.global.common.BaseResponse;
import com.ssafy.umzip.global.common.StatusCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/company")
@RequiredArgsConstructor
@Slf4j
public class CompanyController {
    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<Object> createCompany(@RequestPart MemberCreateRequestDto memberCreateRequestDto,
                                                @RequestPart List<CompanyCreateRequestDto> companyCreateRequestDtoList,
                                                @RequestPart(required = false) MultipartFile deliveryCertificate,
                                                @RequestPart(value = "deliveryImg", required = false) MultipartFile deliveryImg,
                                                @RequestPart(value = "cleanImg", required = false) MultipartFile cleanImg) {

        companyService.createCompany(memberCreateRequestDto, companyCreateRequestDtoList, deliveryCertificate, deliveryImg, cleanImg);

        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(StatusCode.SUCCESS));
    }

    @GetMapping("/{companyId}")
    public ResponseEntity<Object> retrieveCompany(@PathVariable Long companyId) {
        CompanyResponseDto response = companyService.retrieveCompany(companyId);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(response));
    }


}
