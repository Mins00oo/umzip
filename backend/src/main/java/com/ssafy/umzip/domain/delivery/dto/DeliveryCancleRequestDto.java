package com.ssafy.umzip.domain.delivery.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeliveryCancleRequestDto {
    private Long mappingId;

    public DeliveryCancleRequestDto(Long mappingId) {
        this.mappingId = mappingId;
    }
}
