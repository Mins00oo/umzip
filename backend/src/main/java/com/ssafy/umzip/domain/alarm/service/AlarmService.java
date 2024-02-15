package com.ssafy.umzip.domain.alarm.service;

import com.ssafy.umzip.domain.alarm.dto.AlarmResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AlarmService {
    ResponseEntity<List<AlarmResponse>> alarmList(int limit, int offset, Long memberId, String role);
}
