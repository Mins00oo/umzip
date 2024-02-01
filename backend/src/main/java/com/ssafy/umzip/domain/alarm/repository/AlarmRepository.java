package com.ssafy.umzip.domain.alarm.repository;

import com.ssafy.umzip.domain.alarm.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {

}
