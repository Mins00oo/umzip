package com.ssafy.umzip.domain.alarm.dto;

import com.ssafy.umzip.domain.alarm.entity.Alarm;
import com.ssafy.umzip.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardAlarmDto {
    private Member toMember;
    private Member fromMember;
    private String imgPath;
    private Boolean read;
    private AlarmType alarmType;
    private String boardTitle;
    private BoardAlarmType boardAlarmType;
    @Builder
    public BoardAlarmDto(Member toMember, Member fromMember, String imgPath, Boolean read, AlarmType alarmType, String boardTitle, BoardAlarmType boardAlarmType) {
        this.toMember = toMember;
        this.fromMember = fromMember;
        this.imgPath = imgPath;
        this.read = read;
        this.alarmType = alarmType;
        this.boardTitle = boardTitle;
        this.boardAlarmType = boardAlarmType;
    }

    /*
        중고 : 구매완료 ( 303 ) 만 알람. 구매자가 판매자에게 알람. ( 후기 )
     */
    public Alarm toSellerAlarmEntity() {
        StringBuilder sb = new StringBuilder();
        checkAlarmType(sb);
        //후기 작성시 알람
        generateSellerContent(sb);
        imgPath = fromMember.getImageUrl();
        return Alarm.builder()
                .member(this.toMember)
                .isRead(false)
                .content(sb.toString())
                .imgPath(this.imgPath)
                .build();
    }

    /*
        도움 :   채택 : 채택 당한사람 ( adoptedBoardHelp )
                댓글 : 글 작성자 ( postComment )
     */
    public Alarm toBoardHelpUserAlarmEntity(String boardTitle) {
        StringBuilder sb = new StringBuilder();
        checkAlarmType(sb);
        String subTitle;
        if (boardTitle.length() >= 8) {
            subTitle = boardTitle.substring(0, 8) + "···";
        } else {
            subTitle = boardTitle;
        }
        generateBoardHelpContent(sb, subTitle);
        imgPath = fromMember.getImageUrl();
        return Alarm.builder()
                .member(this.toMember)
                .isRead(false)
                .content(sb.toString())
                .imgPath(this.imgPath)
                .build();
    }

    private void generateBoardHelpContent(StringBuilder sb, String subTitle) {
        switch (this.boardAlarmType){
            case ADOPTED->{//채택 당한사람
                sb.append("도움 ["+ subTitle +"]"+"게시글에서 채택되셨어요!");
            }
            case COMMENT->{//댓글 달리면
                sb.append("도움 ["+ subTitle +"]"+"게시글에 "+ fromMember.getName()+"님께서 댓글을 달았어요!");
            }
            case REVIEW -> { //리뷰
                sb.append(fromMember.getName()+" 님께서 후기를 남겨주셨어요!");
            }
        }
    }

    private void checkAlarmType(StringBuilder sb) {
        switch (this.alarmType) {
            case HELP -> {
                sb.append("[ 도움 ] ");
            }
            case TRADE -> {
                sb.append("[ 중고 ] ");
            }
        }
    }

    private void generateSellerContent(StringBuilder sb) {
        sb.append(fromMember.getName() + " 님께서 후기를 남겨주셨어요! ");
    }

}
