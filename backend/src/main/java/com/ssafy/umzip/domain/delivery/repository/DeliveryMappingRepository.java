package com.ssafy.umzip.domain.delivery.repository;

import com.ssafy.umzip.domain.delivery.entity.Car;
import com.ssafy.umzip.domain.delivery.entity.Delivery;
import com.ssafy.umzip.domain.delivery.entity.DeliveryMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DeliveryMappingRepository extends JpaRepository<DeliveryMapping, Long> {
    Boolean existsByDeliveryIdAndMemberId(Long memberId,Long deliveryId);
    Boolean existsByDeliveryIdAndCompanyId(Long deliveryId,Long companyId);
}
