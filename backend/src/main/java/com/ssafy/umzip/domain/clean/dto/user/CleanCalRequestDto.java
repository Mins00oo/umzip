package com.ssafy.umzip.domain.clean.dto.user;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CleanCalRequestDto {
    private String  reservationTime;
    private int roomSize;
    private int roomCount;
    private int windowCount;
    private boolean balconyExistence;
    private boolean duplex;
    private boolean mold;
    private boolean externalWindow;
    private boolean houseSyndrome;
    private boolean removeSticker;
    @Builder
    public CleanCalRequestDto(String reservationTime, int roomSize, int roomCount, int windowCount, boolean balconyExistence, boolean duplex, boolean mold, boolean externalWindow, boolean houseSyndrome, boolean removeSticker) {
        this.reservationTime = (reservationTime);
        this.roomSize = roomSize;
        this.roomCount = roomCount;
        this.windowCount = windowCount;
        this.balconyExistence = balconyExistence;
        this.duplex = duplex;
        this.mold = mold;
        this.externalWindow = externalWindow;
        this.houseSyndrome = houseSyndrome;
        this.removeSticker = removeSticker;
    }
}

