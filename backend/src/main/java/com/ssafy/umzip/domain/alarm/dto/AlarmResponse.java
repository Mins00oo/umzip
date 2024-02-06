package com.ssafy.umzip.domain.alarm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlarmResponse {
    private long alarmId;
    private long memberId;
    private String content;
    private boolean isRead;
    private LocalDateTime createDt;
    private String imgPath;

    public AlarmResponse(long alarmId, String content, boolean isRead, LocalDateTime createDt,String imgPath) {
        this.alarmId = alarmId;
        this.content = content;
        this.isRead = isRead;
        this.createDt = createDt;
        this.imgPath = imgPath;
    }
}
