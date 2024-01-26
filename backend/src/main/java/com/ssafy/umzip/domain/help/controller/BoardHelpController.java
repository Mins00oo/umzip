package com.ssafy.umzip.domain.help.controller;

import com.ssafy.umzip.domain.code.entity.CodeSmall;
import com.ssafy.umzip.domain.help.dto.BoardHelpListDto;
import com.ssafy.umzip.domain.help.dto.BoardHelpListRequestDto;
import com.ssafy.umzip.domain.help.dto.BoardHelpPostRequestDto;
import com.ssafy.umzip.domain.help.entity.BoardHelp;
import com.ssafy.umzip.domain.help.service.BoardHelpService;
import com.ssafy.umzip.domain.help.service.BoardHelpServiceImpl;
import com.ssafy.umzip.global.common.BaseResponse;
import com.ssafy.umzip.global.common.StatusCode;
import com.ssafy.umzip.global.exception.BaseException;
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

    /*
    * 도움 게시글 작성
    * */
    @PostMapping
    public ResponseEntity<Object> postBoardHelp(
            @RequestPart(value="boardHelp") BoardHelpPostRequestDto requestDto,
            @RequestPart(value="imageFileList", required = false)List<MultipartFile> files) { // , required = false 꼭 필요한게 아니다.

        // 1. 사용자 token에서 시군구와 member_id 가져오기
        // 2. boardHelp에 member_id 연결하기


        service.postBoardHelp(requestDto, files);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(StatusCode.SUCCESS));
    }

    /*
    * 도움 게시글 목록 조회 + 도움 게시글 검색
    * 현재 사용자의 시군구 데이터를 토대로 근처 게시글만 조회한다.
    * 검색어를 입력 받으면 like 방식으로 제목을 조회한다.
    * */
    @GetMapping()
    public ResponseEntity<Object> listBoardHelp(
            @RequestParam("code-small") int codeSmallId,
            @RequestParam(defaultValue = "")String keyword,
            @PageableDefault(sort="id", direction = Sort.Direction.DESC) Pageable pageable
            ) {

        // code-small이 잘못된 값이면 예외처리
        // 0: default
        if (codeSmallId != 401 && codeSmallId != 402 && codeSmallId != 403 && codeSmallId != 0) {
            throw new BaseException(StatusCode.CODE_DOES_NOT_EXIST);
        }

        if (keyword == null) {
            keyword = "";
        }

        System.out.println("Controller");
        System.out.println("code-small: " + codeSmallId);
        System.out.println("keyword: " + keyword);
        System.out.println("pageable: " + pageable);

        // accessToken 이 있는지 판단한다. 없으면 로그인 창으로 이동하게끔 에러 코드를 보낸다.

        // accessToken 까서 시군구 데이터 service 로 넘겨야 한다.
        int sigungu = 100;

        // DTO 생성
        BoardHelpListRequestDto requestDto = BoardHelpListRequestDto.builder()
                .sigungu(sigungu) // 작성자 시군구
                .keyword(keyword)
                .build();

        // service
        Page<BoardHelpListDto> BoardHelpPage = service.listBoardHelp(requestDto, pageable);
        
        // 안 나옴
        System.out.println("--------------");
        System.out.println("Controller");
        for (BoardHelpListDto dto : BoardHelpPage) {
            System.out.println(dto.getId() + " " + dto.getTitle());
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(BoardHelpPage));
    }
    
    /*
    * 도움 게시글 목록 조회 + 카테고리 구분
    * */
}
