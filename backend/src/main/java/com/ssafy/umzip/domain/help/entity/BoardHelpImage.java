package com.ssafy.umzip.domain.help.entity;

import com.ssafy.umzip.global.common.BaseTimeEntity;
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

//    @ManyToOne
//    @JoinColumn(name="board_help_id")
//    private BoardHelp boardHelpId;

    @Column(name="board_help_image_origin_name")
    private String imageOriginName;

    @Column(name="board_help_image_saved_name")
    private String imageSavedName;

    @Column(name="board_help_image_path")
    private String imagePath;

    @Builder
    public BoardHelpImage(String imageOriginName, String imageSavedName, String imagePath) {
        this.imageOriginName = imageOriginName;
        this.imageSavedName = imageSavedName;
        this.imagePath = imagePath;
    }

}
