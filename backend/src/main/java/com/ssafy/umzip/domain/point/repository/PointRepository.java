package com.ssafy.umzip.domain.point.repository;

import com.ssafy.umzip.domain.point.entity.Point;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PointRepository extends JpaRepository<Point, Long> {
    List<Point> findAllByMemberId(Long memberId, Pageable pageable);
}
