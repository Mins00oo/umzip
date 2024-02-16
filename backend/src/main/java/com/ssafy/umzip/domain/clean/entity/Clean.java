package com.ssafy.umzip.domain.clean.entity;

import com.ssafy.umzip.domain.delivery.entity.DeliveryImage;
import com.ssafy.umzip.domain.delivery.entity.DeliveryMapping;
import com.ssafy.umzip.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Clean extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clean_id")
    private Long id;

    @OneToMany(mappedBy = "clean", cascade = CascadeType.PERSIST)
    private List<CleanImage> cleanImages = new ArrayList<>();
    @OneToMany(mappedBy = "clean", cascade = CascadeType.PERSIST)
    private List<CleanMapping> cleanMappings = new ArrayList<>();

    @Column(name = "clean_reservation_time")
    private LocalDateTime reservationTime;
    @Column(name = "clean_room_size")
    private Integer roomSize;
    @Column(name = "clean_room_count")
    private Integer roomCount;
    @Column(name = "clean_balcony_existence")
    private Boolean balconyExistence;
    @Column(name = "clean_window_count")
    private Integer windowCount;
    @Column(name = "clean_duplex_room")
    private Boolean duplexRoom;
    @Column(name = "clean_option_mold")
    private Boolean mold;
    @Column(name = "clean_option_external_window")
    private Boolean externalWindow;
    @Column(name = "clean_option_house_syndrome")
    private Boolean houseSyndrome;
    @Column(name = "clean_option_remove_sticker")
    private Boolean removeSticker;
    @Column(name = "clean_sigungu")
    private Integer sigungu;
    @Column(name = "clean_address")
    private String address;
    @Column(name = "clean_address_detail")
    private String addressDetail;
    @Builder
    public Clean(Long id, List<CleanImage> cleanImages, List<CleanMapping> cleanMappings, LocalDateTime reservationTime, Integer roomSize, Integer roomCount, Boolean balconyExistence, Integer windowCount, Boolean duplexRoom, Boolean mold, Boolean externalWindow, Boolean houseSyndrome, Boolean removeSticker, Integer sigungu, String address, String addressDetail) {
        this.id = id;
        this.cleanImages = cleanImages;
        this.cleanMappings = cleanMappings;
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

    public void addImage(final CleanImage image){
        cleanImages.add(image);
    }
    public void addMapping(final CleanMapping mapping){
        cleanMappings.add(mapping);
    }
}
