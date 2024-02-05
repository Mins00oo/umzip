package com.ssafy.umzip.domain.dashboard.controller;

import com.ssafy.umzip.domain.clean.service.CleanUserService;
import com.ssafy.umzip.domain.dashboard.dto.DashBoardReservationResponseDto;
import com.ssafy.umzip.domain.dashboard.service.DashBoardService;
import com.ssafy.umzip.domain.delivery.service.DeliveryUserService;
import com.ssafy.umzip.global.common.BaseResponse;
import com.ssafy.umzip.global.common.StatusCode;
import com.ssafy.umzip.global.util.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dashboard")
public class DashBoardController {
    private final DashBoardService dashBoardService;
    private final JwtTokenProvider jwtTokenProvider;
    @GetMapping
    public ResponseEntity<Object> getAllReservationList(HttpServletRequest request){
        Long memberId = jwtTokenProvider.getId(request);
        List<DashBoardReservationResponseDto> allReservationList = dashBoardService.getAllReservationList(memberId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(allReservationList));
    }



}
