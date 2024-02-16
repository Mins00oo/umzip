package com.ssafy.umzip.domain.clean.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CleanCompanyListRequestDto {
    private String reservationTime;
    private int sigungu;
    private int limit;
    @Builder
    public CleanCompanyListRequestDto(String reservationTime, int sigungu, int limit) {
        this.reservationTime = reservationTime;
        this.sigungu = sigungu;
        this.limit = limit;
    }
}
