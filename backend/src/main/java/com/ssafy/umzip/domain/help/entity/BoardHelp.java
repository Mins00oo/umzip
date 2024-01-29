package com.ssafy.umzip.domain.help.entity;

import com.ssafy.umzip.domain.code.entity.CodeSmall;
import com.ssafy.umzip.domain.member.entity.Member;
import com.ssafy.umzip.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="board_help")
public class BoardHelp extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="board_help_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="code_small_id")
    private CodeSmall codeSmall;

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

//    @OneToMany(mappedBy = "BoardHelp", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
//    //@OrderBy("id")
//    private List<BoardHelpComment> commentIdList = new ArrayList<>();

    // 글 작성
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

    // 글 목록 조회 결과( 카테고리(전체), 검색, 시군구 )
//    @Builder
//    public BoardHelp(Long id, Member member, CodeSmall codeSmall,
//                     String title, int point, int readCnt, int sigungu) {
//        this.id = id;
//        this.member = member;           // 작성자 이름을 가져오기 위함
//        this.codeSmall = codeSmall;
//        this.title = title;
//        this.point = point;
//        this.readCnt = readCnt;
//        this.sigungu = sigungu;
//        // 날짜 정보도 필요한데 어떻게 가져오지?
//        // getCreateDt()
//    }
}
