package com.ssafy.umzip.domain.delivery.repository;

import com.ssafy.umzip.domain.code.entity.CodeSmall;
import com.ssafy.umzip.domain.delivery.dto.CompanyReservationDto;
import com.ssafy.umzip.domain.delivery.dto.DeliveryQuotationRequestDto;
import com.ssafy.umzip.domain.delivery.dto.UserDeliveryMappingDto;
import com.ssafy.umzip.domain.delivery.dto.UserReservationDto;

import java.util.List;

public interface DeliveryMappingCustomRepository {
     List<UserReservationDto> findUserReservationInfo(Long memberId);
     List<CompanyReservationDto> findCompanyReservationInfo(Long companyId);
     Boolean updateDeliveryMappingDetailAndReissuingAndCodeSmall(DeliveryQuotationRequestDto dto,CodeSmall codeSmall);
}
