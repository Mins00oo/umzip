package com.ssafy.umzip.domain.help.controller;

import com.ssafy.umzip.domain.code.entity.CodeSmall;
import com.ssafy.umzip.domain.help.dto.*;
import com.ssafy.umzip.domain.help.entity.BoardHelp;
import com.ssafy.umzip.domain.help.service.BoardHelpService;
import com.ssafy.umzip.domain.help.service.BoardHelpServiceImpl;
import com.ssafy.umzip.global.common.BaseResponse;
import com.ssafy.umzip.global.common.StatusCode;
import com.ssafy.umzip.global.exception.BaseException;
import com.ssafy.umzip.global.util.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/helps")
@RequiredArgsConstructor
@Slf4j
public class BoardHelpController {

    private final BoardHelpService service;
    private final JwtTokenProvider jwtTokenProvider;

    /*
    * [ 도움 게시글 작성 ]
    * */
    @PostMapping
    public ResponseEntity<Object> postBoardHelp(
            @RequestPart(value="boardHelp") BoardHelpPostRequestDto requestDto,
            @RequestPart(value="imageFileList", required = false) List<MultipartFile> imageFileList,
            HttpServletRequest request) {

        Long memberId = jwtTokenProvider.getId(request);
        int sigungu = jwtTokenProvider.getSigungu(request);

        if (imageFileList==null){
            imageFileList = new ArrayList<>();
        }

        service.postBoardHelp(memberId, sigungu, requestDto, imageFileList);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(StatusCode.SUCCESS));
    }

    /*
    * [ 도움 게시글 목록 조회 ]
    *
    * 현재 사용자의 시군구 데이터를 토대로 근처 게시글만 조회한다.
    * 검색어를 입력 받으면 like 방식으로 제목을 조회한다.
    * page는 service에서 -1 처리
    * */
    @GetMapping()
    public ResponseEntity<Object> listBoardHelp(
            @RequestParam("code-small") Long codeSmallId,
            @RequestParam(defaultValue = "")String keyword,
            @PageableDefault(sort="id", direction = Sort.Direction.DESC) Pageable pageable,
            HttpServletRequest request
            ) {

        // 0: default, 403: "도와줬어요"라는 카테고리 없음
        if (codeSmallId != 401 && codeSmallId != 402 && codeSmallId != 0) {
            throw new BaseException(StatusCode.NOT_EXIST_CODE);
        }

        Long memberId = jwtTokenProvider.getId(request);
        int sigungu = jwtTokenProvider.getSigungu(request);

        BoardHelpListRequestDto requestDto = BoardHelpListRequestDto.builder()
                .sigungu(sigungu)
                .keyword(keyword)
                .codeSmallId(codeSmallId)
                .memberId(memberId)
                .build();

        Page<BoardHelpListDto> responseDto = service.listBoardHelp(requestDto, pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(responseDto));
    }

    /* [ 댓글 작성 ]
     *
     */
    @PostMapping("detail/comments/{boardId}")
    public ResponseEntity<Object> postComment(@PathVariable("boardId") Long boardId,
                                              @RequestBody PostCommentRequestDto dto,
                                              HttpServletRequest request) {
        System.out.println("comment: " + dto.getComment());
        Long memberId = jwtTokenProvider.getId(request);

        CommentRequestDto requestDto = new CommentRequestDto(boardId, memberId, dto.getComment());

        service.postComment(requestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(StatusCode.SUCCESS));
    }

    /* [ 도움 게시글 상세조회 ]
    *
    * */
    @GetMapping("/detail/{boardId}")
    public ResponseEntity<Object> detailBoardHelp(@PathVariable("boardId") Long boardId,
                                                  HttpServletRequest request) {

        Long memberId = jwtTokenProvider.getId(request);

        BoardHelpDetailDto responseDto = service.detailBoardHelp(BoardHelpDetailRequestDto.builder()
                        .boardId(boardId)
                        .memberId(memberId)
                        .build());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(responseDto));
    }
    
    /*[ 도움 게시글 상세조회 - 채택 기능 ]
    * 
    * */
    @PostMapping("/detail/comments/adopt/{commentId}")
    public ResponseEntity<Object> adoptedBoardHelp(@PathVariable("commentId") Long commentId,
                                                   HttpServletRequest request) {

        Long memberId = jwtTokenProvider.getId(request);

        service.adoptedBoardHelp(BoardHelpAdopt.builder()
                                .memberId(memberId)
                                .commentId(commentId)
                                .build());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(StatusCode.SUCCESS));
    }

    /*[ 도움 구인 ]
    * 나와 상대방의 도움 구인글을 구분한다 - memberId
    * */
    @GetMapping("/profiles/help-me")
    public ResponseEntity<Object> profileBoardHelpMe(@RequestParam("memberId") Long memberId,
                                                     @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                                     HttpServletRequest request) {

        Long curMemberId = jwtTokenProvider.getId(request);
        boolean isSameMember = false;
        if (Objects.equals(curMemberId, memberId)) {
            isSameMember = true;
        }

        ProfileHelpMeRequestDto requestDto = ProfileHelpMeRequestDto.builder()
                .viewMemberId(memberId)
                .isSameMember(isSameMember)
                .build();

        Page<ProfileHelpMeDto> responseDto = service.listProfileBoardHelpMe(requestDto, pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(responseDto));
    }

    /*[ 도움 공고 ]
    * 나와 상대방의 도움 공고글을 구분한다 - memberId
    * */
    @GetMapping("/profiles/help-you")
    public ResponseEntity<Object> profileBoardHelpYou(@RequestParam("memberId") Long memberId,
                                                     @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                                     HttpServletRequest request) {

        Long curMemberId = jwtTokenProvider.getId(request);
        boolean isSameMember = false;
        if (Objects.equals(curMemberId, memberId)) {
            isSameMember = true;
        }

        ProfileHelpYouRequestDto requestDto = ProfileHelpYouRequestDto.builder()
                .viewMemberId(memberId)
                .isSameMember(isSameMember)
                .build();

        Page<ProfileHelpYouDto> responseDto = service.listProfileBoardHelpYou(requestDto, pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(responseDto));
    }
}
