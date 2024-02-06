package com.ssafy.umzip.domain.help.dto;

import com.ssafy.umzip.domain.help.entity.BoardHelp;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

@Getter
public class ProfileHelpDto {

    private String title;
    private Long codeSmallId;
    private String codeSmallName;
    private int point;
    private String writerName;
    private LocalDateTime createDt;

    @Builder
    public ProfileHelpDto(String title, Long codeSmallId, String codeSmallName,
                          int point, String writerName, LocalDateTime createDt) {
        this.title = title;
        this.codeSmallId = codeSmallId;
        this.codeSmallName = codeSmallName;
        this.point = point;
        this.writerName = writerName;
        this.createDt = createDt;
    }

    public static Page<ProfileHelpDto> toDto(Page<BoardHelp> entityPage) {
        return entityPage.map(boardHelp -> ProfileHelpDto.builder()
                .title(boardHelp.getTitle())
                .codeSmallId(boardHelp.getCodeSmall().getId())
                .codeSmallName(boardHelp.getCodeSmall().getName())
                .point(boardHelp.getPoint())
                .writerName(boardHelp.getMember().getName())
                .createDt(boardHelp.getCreateDt())
                .build());
    }
}
