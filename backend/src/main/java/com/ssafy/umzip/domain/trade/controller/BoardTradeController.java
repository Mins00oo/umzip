package com.ssafy.umzip.domain.trade.controller;

import com.ssafy.umzip.domain.trade.dto.*;
import com.ssafy.umzip.domain.trade.service.BoardTradeService;
import com.ssafy.umzip.global.common.BaseResponse;
import com.ssafy.umzip.global.common.StatusCode;
import com.ssafy.umzip.global.util.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

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
    public ResponseEntity<Object> postBoardTrade(@RequestPart("boardTrade") PostRequestDto requestDto,
                                                 @RequestPart("imageFileList") List<MultipartFile> imageFileList,
                                                 HttpServletRequest request) {

        Long memberId = jwtTokenProvider.getId(request);
        int memberSigungu = jwtTokenProvider.getSigungu(request);

        service.PostBoardTrade(requestDto, imageFileList, memberId, memberSigungu);

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

    /*[ 판매 물품 ]
    * - 나의 판매 물품과 상대방의 판매 물품을 구분해야 한다.
    * */
    @GetMapping("/profiles")
    public ResponseEntity<Object> profileListBoardTrade(@RequestParam("memberId") Long memberId,
                                                        @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                                        HttpServletRequest request) {

        Long curMemberId = jwtTokenProvider.getId(request);
        boolean isSameMember = false;
        if (Objects.equals(curMemberId, memberId)) {
            isSameMember = true;
        }

        ProfileListRequestDto profileListRequestDto = ProfileListRequestDto.builder()
                .viewMemberId(memberId)
                .isSameMember(isSameMember)
                .build();

        Page<ProfileListDto> listDto = service.profileListBoardTrade(profileListRequestDto, pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(listDto));
    }

    /*[ 구매 물품 ]
    * - 나의 구매 물품과 상대방의 구매 물품을 구분해야 한다.
    * */
}
