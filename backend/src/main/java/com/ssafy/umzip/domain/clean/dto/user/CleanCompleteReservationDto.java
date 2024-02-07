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
    @Builder
    public CleanCompleteReservationDto(Long mappingId) {
        this.mappingId = mappingId;
    }
}
