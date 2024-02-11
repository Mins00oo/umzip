package com.ssafy.umzip.domain.clean.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CleanCompleteReservationDto {
    private Long mappingId;
    private int point; //minus point
    @Builder
    public CleanCompleteReservationDto(Long mappingId, int point) {
        this.mappingId = mappingId;
        this.point = point;
    }
}
