package com.ssafy.umzip.domain.clean.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CleanDetailResponseDto {
    private Long id;
    private LocalDateTime reservationTime;
    private int roomSize;
    private int roomCount;
    private Boolean balconyExistence;
    private int windowCount;
    private Boolean duplexRoom;
    private Boolean mold;
    private Boolean externalWindow;
    private Boolean houseSyndrome;
    private Boolean removeSticker;
    private int sigungu;
    private String address;
    private String addressDetail;
    private List<String> cleanImages = new ArrayList<>();
    @Builder
    public CleanDetailResponseDto(Long id, LocalDateTime reservationTime, int roomSize, int roomCount, Boolean balconyExistence, int windowCount, Boolean duplexRoom, Boolean mold, Boolean externalWindow, Boolean houseSyndrome, Boolean removeSticker, int sigungu, String address, String addressDetail) {
        this.id = id;
        this.reservationTime = reservationTime;
        this.roomSize = roomSize;
        this.roomCount = roomCount;
        this.balconyExistence = balconyExistence;
        this.windowCount = windowCount;
        this.duplexRoom = duplexRoom;
        this.mold = mold;
        this.externalWindow = externalWindow;
        this.houseSyndrome = houseSyndrome;
        this.removeSticker = removeSticker;
        this.sigungu = sigungu;
        this.address = address;
        this.addressDetail = addressDetail;
    }
}
