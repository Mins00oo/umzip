package com.ssafy.umzip.domain.trade.controller;

import com.ssafy.umzip.domain.help.dto.BoardHelpPostRequestDto;
import com.ssafy.umzip.domain.member.entity.Member;
import com.ssafy.umzip.domain.trade.dto.*;
import com.ssafy.umzip.domain.trade.entity.BoardTrade;
import com.ssafy.umzip.domain.trade.service.BoardTradeService;
import com.ssafy.umzip.global.common.BaseResponse;
import com.ssafy.umzip.global.common.StatusCode;
import com.ssafy.umzip.global.util.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/trade-items")
@RequiredArgsConstructor
@Slf4j
public class BoardTradeController {

    private final BoardTradeService service;
    private final JwtTokenProvider jwtTokenProvider;

    /*[ 중고 게시글 작성 ]
    *
    * */
    @PostMapping
    public ResponseEntity<Object> postBoardTrade(@RequestPart(value="boardTrade") PostRequestDto requestDto,
                                                 @RequestPart(value="imageFileList") List<MultipartFile> files,
                                                 HttpServletRequest request) {

        Long memberId = jwtTokenProvider.getId(request);
        int memberSigungu = jwtTokenProvider.getSigungu(request);

        requestDto.setMemeber(memberId, memberSigungu);
        service.PostBoardTrade(requestDto, files);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(StatusCode.SUCCESS));
    }

    /*[ 중고 게시글 목록 조회 ]
    *
    * */
    @GetMapping()
    public ResponseEntity<Object> listBoardTrade(@RequestParam(defaultValue = "")String keyword,
                                                 @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                                 HttpServletRequest request) {

        Long memberId = jwtTokenProvider.getId(request);
        int sigungu = jwtTokenProvider.getSigungu(request);

        ListRequestDto requestDto = ListRequestDto.builder()
                .memberId(memberId)
                .sigungu(sigungu)
                .keyword(keyword)
                .build();

        Slice<ListDto> listDto = service.listBoardTrade(requestDto, pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(listDto));
    }

    /*[ 중고 게시글 상세조회 ]
    *
    * */
    @GetMapping("/detail/{boardId}")
    public ResponseEntity<Object> detailBoardTrade(@PathVariable("boardId") Long boardId,
                                                   HttpServletRequest request) {

        Long memberId = jwtTokenProvider.getId(request);
        DetailRequestDto requestDto = DetailRequestDto.builder()
                .memberId(memberId)
                .boardId(boardId)
                .build();

        DetailDto detailDto = service.detailBoardTrade(requestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(detailDto));
    }
}
