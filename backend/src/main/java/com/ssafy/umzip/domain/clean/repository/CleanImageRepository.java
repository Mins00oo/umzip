package com.ssafy.umzip.domain.clean.repository;

import com.ssafy.umzip.domain.clean.entity.CleanImage;
import com.ssafy.umzip.domain.delivery.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CleanImageRepository  extends JpaRepository<CleanImage, Long> {
}
