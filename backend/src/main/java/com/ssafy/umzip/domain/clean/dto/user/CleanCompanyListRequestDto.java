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
    @Builder
    public CleanCompanyListRequestDto(String reservationTime, int sigungu) {
        this.reservationTime = reservationTime;
        this.sigungu = sigungu;
    }
}
