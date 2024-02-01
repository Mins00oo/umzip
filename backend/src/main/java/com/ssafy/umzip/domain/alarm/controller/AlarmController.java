package com.ssafy.umzip.domain.alarm.controller;

import com.ssafy.umzip.domain.alarm.service.AlarmService;
import com.ssafy.umzip.domain.tag.dto.TagListByLargeCodeResponce;
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
    public ResponseEntity<Object> createReview(@RequestParam int limit, @RequestParam int offset, HttpServletRequest request) {
        // token값에서 작성자 ID 획득
        Long userId = jwtTokenProvider.getId(request);
        
        return null;
    }
}