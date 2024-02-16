package com.ssafy.umzip.domain.clean.entity;

import com.ssafy.umzip.global.util.s3.S3UploadDto;
import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
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
    @Builder
    public CleanImage(Clean clean, S3UploadDto uploadDto) {
        this.clean = clean;
        this.path = uploadDto.getImgUrl();
        this.originalName = uploadDto.getOriginName();
        this.fileName = uploadDto.getSaveName();
    }
}
