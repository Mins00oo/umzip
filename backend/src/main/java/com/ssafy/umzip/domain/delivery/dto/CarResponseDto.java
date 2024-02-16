package com.ssafy.umzip.domain.delivery.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
@NoArgsConstructor
public class CarResponseDto {
    private Long carId;
    private String name;
    private String description;
    @Builder
    public CarResponseDto(Long carId, String name, String description) {
        this.carId = carId;
        this.name = name;
        this.description = description;
    }
}
