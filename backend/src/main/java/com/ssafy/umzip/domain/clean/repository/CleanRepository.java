package com.ssafy.umzip.domain.clean.repository;

import com.ssafy.umzip.domain.clean.entity.Clean;
import com.ssafy.umzip.domain.delivery.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CleanRepository  extends JpaRepository<Clean, Long> {
}
