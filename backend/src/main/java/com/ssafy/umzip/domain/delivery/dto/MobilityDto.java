package com.ssafy.umzip.domain.delivery.dto;

import lombok.Data;
import lombok.Getter;

@Getter
public class MobilityDto {
    private int taxi;
    private int duration;//초
    private int toll;
    private int distance;//미터

    public MobilityDto(int taxi, int duration, int toll, int distance) {
        this.taxi = taxi;
        this.duration = duration;
        this.toll = toll;
        this.distance = distance;
    }
}
