package com.ssafy.umzip.domain.clean.repository;

import com.ssafy.umzip.domain.clean.dto.company.CleanQuotationRequestDto;
import com.ssafy.umzip.domain.clean.dto.user.CleanReservationRequestDto;
import com.ssafy.umzip.domain.clean.dto.user.UserCleanReservationResponseDto;
import com.ssafy.umzip.domain.code.entity.CodeSmall;

import java.util.List;

public interface CleanCustomRepository {
    Boolean updateCodeAndReissuingAndDetail(CleanQuotationRequestDto dto, CodeSmall codeSmall);
    List<UserCleanReservationResponseDto> findUserReservationInfo(Long memberId);
}
