package com.ssafy.umzip.domain.delivery.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeliveryCancelRequestDto {
    private Long mappingId;

    public DeliveryCancelRequestDto(Long mappingId) {
        this.mappingId = mappingId;
    }
}
