package com.ssafy.umzip.domain.delivery.repository;

import com.ssafy.umzip.domain.code.entity.CodeSmall;
import com.ssafy.umzip.domain.delivery.dto.DeliveryQuotationRequestDto;
import com.ssafy.umzip.domain.delivery.dto.UserDeliveyMappingDto;
import com.ssafy.umzip.domain.delivery.entity.DeliveryMapping;

import java.util.List;

public interface DeliveryMappingCustomRepository {
     List<Long> findDistinctDeliveryIdsByMemberId(Long memberId);
     List<UserDeliveyMappingDto> findAllDeliveryMappingsWithCompany(Long deliveryId);
     Boolean updateDeliveryMappingDetailAndReissuingAndCodeSmall(DeliveryQuotationRequestDto dto,CodeSmall codeSmall);
}
