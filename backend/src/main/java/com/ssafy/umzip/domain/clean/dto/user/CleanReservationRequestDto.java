package com.ssafy.umzip.domain.clean.dto.user;

import com.ssafy.umzip.domain.clean.entity.Clean;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

import static com.ssafy.umzip.global.common.CommonMethods.getLocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class CleanReservationRequestDto {
    private String reservationTime;
    private String address;
    private String addressDetail;
    private int sigungu;
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
    public CleanReservationRequestDto(String reservationTime, String address, String addressDetail, int sigungu, int roomSize, int roomCount, int windowCount, boolean balconyExistence, boolean duplex, boolean mold, boolean externalWindow, boolean houseSyndrome, boolean removeSticker) {
        this.reservationTime = reservationTime;
        this.address = address;
        this.addressDetail = addressDetail;
        this.sigungu = sigungu;
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
    public static Clean toEntity(CleanReservationRequestDto dto){
        return Clean.builder()
                .reservationTime(getLocalDateTime(dto.getReservationTime()))
                .roomSize(dto.getRoomSize())
                .roomCount(dto.getRoomCount())
                .balconyExistence(dto.isBalconyExistence())
                .windowCount(dto.getWindowCount())
                .duplexRoom(dto.isDuplex())
                .mold(dto.isMold())
                .externalWindow(dto.isExternalWindow())
                .houseSyndrome(dto.isHouseSyndrome())
                .removeSticker(dto.isRemoveSticker())
                .sigungu(dto.getSigungu())
                .address(dto.getAddress())
                .addressDetail(dto.getAddressDetail())
                .cleanImages(new ArrayList<>())
                .cleanMappings(new ArrayList<>())
                .build();
    }

}
