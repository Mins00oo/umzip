package com.ssafy.umzip.domain.clean.repository;

import com.ssafy.umzip.domain.clean.dto.company.CleanCompanyReservationResponseDto;
import com.ssafy.umzip.domain.clean.dto.company.CleanQuotationRequestDto;
import com.ssafy.umzip.domain.clean.dto.user.CleanCompanyListRequestDto;
import com.ssafy.umzip.domain.clean.dto.user.CleanMatchingCompanyDto;
import com.ssafy.umzip.domain.clean.dto.user.CleanReservationRequestDto;
import com.ssafy.umzip.domain.clean.dto.user.UserCleanReservationResponseDto;
import com.ssafy.umzip.domain.code.entity.CodeSmall;

import java.util.List;

public interface CleanCustomRepository {
    Boolean updateCodeAndReissuingAndDetail(CleanQuotationRequestDto dto, CodeSmall codeSmall);
    List<UserCleanReservationResponseDto> findUserReservationInfo(Long memberId);
    List<CleanMatchingCompanyDto> findCompanyMatchingList(CleanCompanyListRequestDto dto);

    List<CleanCompanyReservationResponseDto> findCompanyReservationInfo(Long companyId);


}
