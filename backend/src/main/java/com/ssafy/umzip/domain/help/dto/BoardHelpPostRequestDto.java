package com.ssafy.umzip.domain.help.dto;

import com.ssafy.umzip.domain.help.entity.BoardHelp;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
public class BoardHelpPostRequestDto {

    private int codeSmall;  // 도움주세요(401), 도움줄게요(403)
    private String title;
    private List<MultipartFile> files;
    private String content;
    private int point;

    // 사용자가 입력한 데이터
    @Builder
    public BoardHelpPostRequestDto(int codeSmall, String title, String content, int point, List<MultipartFile> files) {
        this.codeSmall = codeSmall;
        this.title = title;
        this.content = content;
        this.point = point;
        this.files = files;
    }

//    public BoardHelp toEntity() {
//        return BoardHelp.builder()
//                .
//    }

}
