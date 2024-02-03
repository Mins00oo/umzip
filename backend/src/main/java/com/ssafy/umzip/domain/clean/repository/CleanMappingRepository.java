package com.ssafy.umzip.domain.clean.repository;

import com.ssafy.umzip.domain.clean.entity.CleanMapping;
import com.ssafy.umzip.domain.delivery.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CleanMappingRepository  extends JpaRepository<CleanMapping, Long> {
    boolean existsByCleanIdAndMemberId(Long cleanId, Long memberId);
}
