package com.ssafy.umzip.domain.delivery.repository;

import com.ssafy.umzip.domain.delivery.entity.Car;
import com.ssafy.umzip.domain.delivery.entity.DeliveryMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryMappingRepository extends JpaRepository<DeliveryMapping, Long> {
}
