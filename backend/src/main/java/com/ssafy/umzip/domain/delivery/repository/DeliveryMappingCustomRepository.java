package com.ssafy.umzip.domain.delivery.repository;

import com.ssafy.umzip.domain.delivery.dto.UserDeliveyMappingDto;
import com.ssafy.umzip.domain.delivery.entity.DeliveryMapping;

import java.util.List;

public interface DeliveryMappingCustomRepository {
    public List<Long> findDistinctDeliveryIdsByMemberId(Long memberId);
    public List<UserDeliveyMappingDto> findAllDeliveryMappingsWithCompany(Long deliveryId);
    long updateDeliveryMappingDetailAndReissuingAndCodeSmall(long mappingId, String detail, long reissuing, long codeSmallId);
}
