package com.ssafy.umzip.domain.trade.controller;

import com.ssafy.umzip.domain.help.dto.BoardHelpPostRequestDto;
import com.ssafy.umzip.domain.member.entity.Member;
import com.ssafy.umzip.domain.trade.dto.PostRequestDto;
import com.ssafy.umzip.domain.trade.entity.BoardTrade;
import com.ssafy.umzip.domain.trade.service.BoardTradeService;
import com.ssafy.umzip.global.common.BaseResponse;
import com.ssafy.umzip.global.common.StatusCode;
import com.ssafy.umzip.global.util.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
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


}
