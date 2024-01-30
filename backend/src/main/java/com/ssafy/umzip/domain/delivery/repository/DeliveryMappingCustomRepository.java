package com.ssafy.umzip.domain.delivery.repository;

import com.ssafy.umzip.domain.code.entity.CodeSmall;

import com.ssafy.umzip.domain.delivery.dto.*;


import java.time.LocalDateTime;
import java.util.List;

public interface DeliveryMappingCustomRepository {
     List<UserReservationDto> findUserReservationInfo(Long memberId);
     List<CompanyReservationDto> findCompanyReservationInfo(Long companyId);
     Boolean updateDeliveryMappingDetailAndReissuingAndCodeSmall(DeliveryQuotationRequestDto dto,CodeSmall codeSmall);
     List<DeliveryMatchingCompanyDto> findCompanyMatchingList(LocalDateTime startTime, LocalDateTime endTime,int sigungu);
}
