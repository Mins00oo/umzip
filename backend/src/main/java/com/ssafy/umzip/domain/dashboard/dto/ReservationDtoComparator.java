package com.ssafy.umzip.domain.dashboard.dto;

import java.util.Comparator;

public class ReservationDtoComparator implements Comparator<ReservationDto> {
    @Override
    public int compare(ReservationDto dto1, ReservationDto dto2) {
        return dto1.getCreateDt().compareTo(dto2.getCreateDt());
    }
}
