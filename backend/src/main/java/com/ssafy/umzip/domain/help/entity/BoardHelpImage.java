package com.ssafy.umzip.domain.help.entity;

import com.ssafy.umzip.global.common.BaseTimeEntity;
import com.ssafy.umzip.global.util.s3.S3UploadDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="board_help_image")
public class BoardHelpImage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="board_help_image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="board_help_id")
    private BoardHelp boardHelp;

    @Column(name="board_help_image_origin_name")
    private String imageOriginName;

    @Column(name="board_help_image_saved_name")
    private String imageSavedName;

    @Column(name="board_help_image_path")
    private String imagePath;

    @Builder
    public BoardHelpImage(S3UploadDto s3UploadDto, BoardHelp boardHelp) {
        this.imageOriginName = s3UploadDto.getOriginName();
        this.imageSavedName = s3UploadDto.getSaveName();
        this.imagePath = s3UploadDto.getImgUrl();
        this.boardHelp = boardHelp;
    }
}
