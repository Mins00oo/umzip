package com.ssafy.umzip.domain.clean.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CleanImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clean_image_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clean_id")
    private Clean clean;
    @Column(name = "clean_image_path")
    private String path;
    @Column(name = "clean_image_original_name")
    private String originalName;
    @Column(name = "clean_image_file_name")
    private String fileName;
}
