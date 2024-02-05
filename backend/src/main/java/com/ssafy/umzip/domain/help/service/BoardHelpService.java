package com.ssafy.umzip.domain.help.service;


import com.ssafy.umzip.domain.help.dto.*;
import com.ssafy.umzip.domain.help.entity.BoardHelp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BoardHelpService {

    void postBoardHelp(Long memberId, int sigungu, BoardHelpPostRequestDto requestDto, List<MultipartFile> files);

    Page<BoardHelpListDto> listBoardHelp(
            BoardHelpListRequestDto requestDto,
            @PageableDefault(sort="id", direction = Sort.Direction.DESC) Pageable pageable);

    void postComment(CommentRequestDto requestDto);

    BoardHelpDetailDto detailBoardHelp(BoardHelpDetailRequestDto requestDto);

    void adoptedBoardHelp(BoardHelpAdopt requestDto);

    Page<ProfileHelpMeDto> listProfileBoardHelpMe(ProfileHelpMeRequestDto requestDto, Pageable pageable);

    Page<ProfileHelpYouDto> listProfileBoardHelpYou(ProfileHelpYouRequestDto requestDto, Pageable pageable);
}
