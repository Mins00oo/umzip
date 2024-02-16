package com.ssafy.umzip.domain.help.service;


import com.ssafy.umzip.domain.help.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BoardHelpService {

    void postBoardHelp(Long memberId, int sigungu, PostHelpRequestDto requestDto, List<MultipartFile> files);

    Page<ListHelpDto> listBoardHelp(
            ListHelpRequestDto requestDto,
            @PageableDefault(sort="id", direction = Sort.Direction.DESC) Pageable pageable);

    void postComment(PostCommentRequestWrapDto requestDto);

    DetailHelpDto detailBoardHelp(DetailHelpRequestDto requestDto);
    ListCommentWithStatusDto commentListBoardHelp(DetailHelpRequestDto requestDto);

    void adoptedBoardHelp(AdoptCommentRequestDto requestDto);

    Page<ProfileDto> listProfileBoardHelpMe(ProfileRequestDto requestDto, Pageable pageable);

    Page<ProfileDto> listProfileBoardHelpYou(ProfileRequestDto requestDto, Pageable pageable);

    void givePointBoardHelp(GivePointRequestDto requestDto);
}
