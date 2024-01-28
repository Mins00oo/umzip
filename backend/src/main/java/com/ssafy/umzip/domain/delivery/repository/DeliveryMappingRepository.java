package com.ssafy.umzip.domain.delivery.repository;

import com.ssafy.umzip.domain.delivery.entity.Car;
import com.ssafy.umzip.domain.delivery.entity.Delivery;
import com.ssafy.umzip.domain.delivery.entity.DeliveryMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeliveryMappingRepository extends JpaRepository<DeliveryMapping, Long> {
    public List<DeliveryMapping> findByMemberId(Long memberId);
}
