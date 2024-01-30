package com.ssafy.umzip.domain.clean.entity;

import com.ssafy.umzip.domain.delivery.entity.DeliveryImage;
import com.ssafy.umzip.domain.delivery.entity.DeliveryMapping;
import com.ssafy.umzip.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private List<CleanImage> images = new ArrayList<>();
    @OneToMany(mappedBy = "clean", cascade = CascadeType.PERSIST)
    private List<CleanMapping> mappings = new ArrayList<>();
    @Column(name = "clean_reservation_time")
    private LocalDateTime reservationTime;
    @Column(name = "clean_room_size")
    private Boolean roomSize;
    @Column(name = "clean_room_count")
    private Boolean roomCount;
    @Column(name = "clean_balcony_existence")
    private Boolean balconyExistence;
    @Column(name = "clean_window_count")
    private Boolean windowCount;
    @Column(name = "clean_duplex_room")
    private Boolean duplexRoom;
    @Column(name = "clean_option_mold")
    private Boolean mold;
    @Column(name = "clean_option_external_window")
    private Boolean ExternalWindow;
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
}
