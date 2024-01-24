package com.ssafy.umzip.domain.help.entity;

import com.ssafy.umzip.domain.code.entity.CodeSmall;
import com.ssafy.umzip.domain.member.entity.Member;
import com.ssafy.umzip.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="board_help")
public class BoardHelp extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="board_help_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name="code_small_id")
    private CodeSmall codeSmall;

//    @OneToMany(mappedBy = "board_help", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
//    private List<BoardHelpImage> imageFileList = new ArrayList<>();

    @Column(name="board_help_title")
    private String title;

    @Column(name="board_help_content")
    private String content;

    @Column(name="board_help_point")
    private int point;

    @Column(name="board_help_read_count")
    private int readCnt;

    @Column(name="board_sigungu")
    private int sigungu;

    @Column(name="board_adopted")
    private Boolean isAdopted;

    // BaseTimeEntity를 상속해서 createDt, updateDt를 대체

    // DB에
    @Builder
    public BoardHelp(String title, String content, int point, int readCnt,
                     int sigungu, Boolean isAdopted,
                     CodeSmall codeSmall, Member member) {
        this.title = title;
        this.content = content;
        this.point = point;
        this.readCnt = readCnt;
        this.sigungu = sigungu;
        this.isAdopted = isAdopted;
        this.codeSmall = codeSmall;
        this.member = member;
    }

//    public void addImage(BoardHelpImage image) {
//        this.imageFileList.add(image);
//        // 게시글에 이미지 파일이 없는 경우
//        if (image.getBoardHelp() != this) {
//            // 파일을 저장한다.
//            image.setBoard(this);
//        }
//    }

}
