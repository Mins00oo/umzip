package com.ssafy.umzip.domain.dashboard.service;

import com.ssafy.umzip.domain.dashboard.dto.DashBoardReservationResponseDto;

import java.util.List;

public interface DashBoardService {
    List<DashBoardReservationResponseDto> getAllReservationList(Long memberId);
}
