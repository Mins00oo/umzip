package com.ssafy.umzip.domain.delivery.entity;

import com.ssafy.umzip.global.util.s3.S3UploadDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    Delivery delivery;

    @Column(name="delivery_image_original_name")
    private String originalName;

    @Column(name="delivery_image_file_name")
    private String fileName;

    @Column(name="delivery_image_path")
    private String path;
    @Builder
    public DeliveryImage(Long id, Delivery delivery, S3UploadDto dto) {
        this.id = id;
        this.delivery = delivery;
        this.originalName = dto.getOriginName();
        this.fileName = dto.getSaveName();
        this.path = dto.getImgUrl();
    }
}
