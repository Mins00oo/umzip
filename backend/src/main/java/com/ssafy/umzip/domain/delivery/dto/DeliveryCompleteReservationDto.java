package com.ssafy.umzip.domain.delivery.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeliveryCompleteReservationDto {
    private Long mappingId;
    private int point;
    @Builder
    public DeliveryCompleteReservationDto(Long mappingId, int point) {
        this.mappingId = mappingId;
        this.point = point;
    }
}
