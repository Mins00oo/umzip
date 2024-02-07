package com.ssafy.umzip.domain.alarm.dto;

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
        중고 : 구매완료 ( 303 ) 만 알람. 구매자가 판매자에게 알람.
     */
    
    /*
        도움 :   채택 : 채택 당한사람 ( adoptedBoardHelp)
                댓글 : 글 작성자 ( postComment )
     */
    private void checkAlarmType(StringBuilder sb) {
        switch (this.alarmType) {
            case HELP -> {
                sb.append("[ 중고 ] ");
            }
            case TRADE -> {
                sb.append("[ 도움 ] ");
            }
        }
    }
}
