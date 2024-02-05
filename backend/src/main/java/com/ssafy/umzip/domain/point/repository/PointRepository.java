package com.ssafy.umzip.domain.point.repository;

import com.ssafy.umzip.domain.point.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point, Long> {
}
