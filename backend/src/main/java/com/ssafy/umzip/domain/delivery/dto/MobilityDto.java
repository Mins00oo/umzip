package com.ssafy.umzip.domain.delivery.dto;

import lombok.Data;
import lombok.Getter;

@Getter
public class MobilityDto {
    private int duration;//초
    private int toll;
    private int distance;//미터

    public MobilityDto(int duration, int toll, int distance) {
        this.duration = duration;
        this.toll = toll;
        this.distance = distance;
    }
}
