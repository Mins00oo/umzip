package com.ssafy.umzip.domain.delivery.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
public class DeliveryCalResponseDto {
    private Long price;
    private LocalDateTime endTime;

    public DeliveryCalResponseDto(Long price, LocalDateTime endTime) {
        this.price = price;
        this.endTime = endTime;
    }
}
