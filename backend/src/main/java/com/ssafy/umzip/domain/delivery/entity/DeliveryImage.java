package com.ssafy.umzip.domain.delivery.entity;

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
    public DeliveryImage(Long id, Delivery delivery, String originalName, String fileName, String path) {
        this.id = id;
        this.delivery = delivery;
        this.originalName = originalName;
        this.fileName = fileName;
        this.path = path;
    }
}
