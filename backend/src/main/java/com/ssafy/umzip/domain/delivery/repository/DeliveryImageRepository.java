package com.ssafy.umzip.domain.delivery.repository;

import com.ssafy.umzip.domain.delivery.entity.Car;
import com.ssafy.umzip.domain.delivery.entity.DeliveryImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryImageRepository extends JpaRepository<DeliveryImage, Long> {
}
