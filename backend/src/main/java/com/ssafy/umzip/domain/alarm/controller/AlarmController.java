package com.ssafy.umzip.domain.alarm.controller;

import com.ssafy.umzip.domain.alarm.dto.AlarmResponse;
import com.ssafy.umzip.domain.alarm.service.AlarmService;
import com.ssafy.umzip.global.util.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alarm")
@RequiredArgsConstructor
@Slf4j
public class AlarmController {
    private final JwtTokenProvider jwtTokenProvider;
    private final AlarmService alarmService;
    @GetMapping()
    public ResponseEntity<List<AlarmResponse>> createReview(@RequestParam int limit, @RequestParam int offset, HttpServletRequest request) {
        Long memberId = jwtTokenProvider.getId(request);
        String role = jwtTokenProvider.getRole(request);
        return alarmService.alarmList(limit, offset, memberId, role);
    }
}