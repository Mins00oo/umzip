package com.ssafy.umzip.domain.trade.controller;

import com.ssafy.umzip.domain.trade.dto.*;
import com.ssafy.umzip.domain.trade.service.BoardTradeService;
import com.ssafy.umzip.global.common.BaseResponse;
import com.ssafy.umzip.global.common.StatusCode;
import com.ssafy.umzip.global.util.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
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

        List<ListDto> responseDto = service.listBoardTrade(requestDto, pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(responseDto));
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
    @GetMapping("/profiles/sell")
    public ResponseEntity<Object> profileSellListBoardTrade(@RequestParam("memberId") Long memberId,
                                                        @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                                        HttpServletRequest request) {

        Long curMemberId = jwtTokenProvider.getId(request);
        boolean isSameMember = false;
        if (Objects.equals(curMemberId, memberId)) {
            isSameMember = true;
        }

        ProfileSellListRequestDto profileSellListRequestDto = ProfileSellListRequestDto.builder()
                .viewMemberId(memberId)
                .isSameMember(isSameMember)
                .build();

        List<ProfileListDto> responseDto = service.profileSellListBoardTrade(profileSellListRequestDto, pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(responseDto));
    }

    /*[ 구매 물품 ]
    * - 나의 구매 물품과 상대방의 구매 물품을 구분해야 한다.
    * */
    @GetMapping("/profiles/buy")
    public ResponseEntity<Object> profileBuyListBoardTrade(@RequestParam("memberId") Long memberId,
                                                           @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                                           HttpServletRequest request) {
        Long curMemberId = jwtTokenProvider.getId(request);
        boolean isSameMember = false;
        if (Objects.equals(curMemberId, memberId)) {
            isSameMember = true;
        }

        ProfileBuyListRequestDto profilebuyListRequestDto = ProfileBuyListRequestDto.builder()
                .viewMemberId(memberId)
                .isSameMember(isSameMember)
                .build();

        List<ProfileListDto> responseDto = service.profileBuyListBoardTrade(profilebuyListRequestDto, pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(responseDto));
    }

    /*[ 판매 완료 ]
     * - 판매 완료 버튼을 누르면 해당 중고 게시글의 상태 코드를 302(판매완료)로 변경한다
     * */

    /*[ 구매 완료 여부 ]
    * 구매 여부: 후기작성을 끝내면 구매완료 테이블에 추가한다.
    * */
}
