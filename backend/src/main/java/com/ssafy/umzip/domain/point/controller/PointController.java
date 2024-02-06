package com.ssafy.umzip.domain.point.controller;

import com.ssafy.umzip.domain.point.dto.PointUsageResponseDto;
import com.ssafy.umzip.domain.point.service.PointService;
import com.ssafy.umzip.global.common.BaseResponse;
import com.ssafy.umzip.global.util.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/point")
@RequiredArgsConstructor
public class PointController {
    private final PointService pointService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping()
    public ResponseEntity<Object> retrieveMyPointUsage(HttpServletRequest request,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Long memberId = jwtTokenProvider.getId(request);
        PointUsageResponseDto response = pointService.retrieveMyPointUsage(memberId, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(response));
    }
}
