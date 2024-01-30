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

import java.util.List;

@RestController
@RequestMapping("/api/helps")
@RequiredArgsConstructor
@Slf4j
public class BoardHelpController {

    private final BoardHelpService service;
    // private final JwtTokenProvider jwtTokenProvider;

    /*
    * [ 도움 게시글 작성 ]
    * */
    @PostMapping
    public ResponseEntity<Object> postBoardHelp(
            @RequestPart(value="boardHelp") BoardHelpPostRequestDto requestDto,
            @RequestPart(value="imageFileList", required = false)List<MultipartFile> files) { // , required = false 꼭 필요한게 아니다.

        // 1. 사용자 token에서 시군구와 member_id 가져오기
        // 인자로 HttpServletRequest request 가져와야 함
        // Long memberId = jwtTokenProvider.getId(request);

        // 2. boardHelp에 member_id 연결하기


        service.postBoardHelp(requestDto, files);

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
            @PageableDefault(sort="id", direction = Sort.Direction.DESC) Pageable pageable  // sort="EntityColumn"
            ) {

        // code-small이 잘못된 값이면 예외처리 : existsBy로 DB에 접근해서 값이 있는지 없는지 체크
        // 0: default
        if (codeSmallId != 401 && codeSmallId != 402 && codeSmallId != 403 && codeSmallId != 0) {
            throw new BaseException(StatusCode.NOT_EXIST_CODE);
        }

        // accessToken 이 있는지 판단한다. 없으면 로그인 창으로 이동하게끔 에러 코드를 보낸다.

        // accessToken 까서 시군구 데이터 service 로 넘겨야 한다.
        int sigungu = 100;

        // DTO 생성
        BoardHelpListRequestDto requestDto = BoardHelpListRequestDto.builder()
                .sigungu(sigungu) // 작성자 시군구
                .keyword(keyword)
                .codeSmallId(codeSmallId)
                .build();

        // service
        Page<BoardHelpListDto> boards = service.listBoardHelp(requestDto, pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(boards));
    }

    /* [ 댓글 작성 ]
     *
     */
    @PostMapping("detail/comments/{boardId}")
    public ResponseEntity<Object> postComment(@PathVariable("boardId") Long boardId,
                                              @RequestParam("comment") String comment) {

        CommentRequestDto requestDto = new CommentRequestDto(boardId, comment);

        service.postComment(requestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(StatusCode.SUCCESS));
    }

    /* [ 도움 게시글 상세보기 ]
    *
    * */
    @GetMapping("/detail/{boardId}")
    public ResponseEntity<Object> detailBoardHelp(@PathVariable("boardId") Long boardId) {

        BoardHelpDetailDto responseDto = service.detailBoardHelp(BoardHelpDetailRequestDto.builder()
                        .boardId(boardId)
                        .build());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(responseDto));
    }
}
