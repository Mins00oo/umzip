package com.ssafy.umzip.domain.help.dto;

import com.ssafy.umzip.domain.help.entity.BoardHelp;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

@ToString
@Getter
public class ProfileHelpMeDto {

    private String title;
    private Long codeSmallId;
    private String codeSmallName;
    private int point;
    private String writerName;
    private LocalDateTime createDt;

    @Builder
    public ProfileHelpMeDto(String title, Long codeSmallId, String codeSmallName,
                            int point, String writerName, LocalDateTime createDt) {
        this.title = title;
        this.codeSmallId = codeSmallId;
        this.codeSmallName = codeSmallName;
        this.point = point;
        this.writerName = writerName;
        this.createDt = createDt;
    }

    public static Page<ProfileHelpMeDto> toDto(Page<BoardHelp> entityPage) {
        return entityPage.map(boardHelp -> ProfileHelpMeDto.builder()
                .title(boardHelp.getTitle())
                .codeSmallId(boardHelp.getCodeSmall().getId())
                .codeSmallName(boardHelp.getCodeSmall().getName())
                .point(boardHelp.getPoint())
                .writerName(boardHelp.getMember().getName())
                .createDt(boardHelp.getCreateDt())
                .build());
    }
}
