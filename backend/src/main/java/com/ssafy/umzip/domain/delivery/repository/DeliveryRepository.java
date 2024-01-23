package com.ssafy.umzip.domain.delivery.repository;

import com.ssafy.umzip.domain.delivery.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
