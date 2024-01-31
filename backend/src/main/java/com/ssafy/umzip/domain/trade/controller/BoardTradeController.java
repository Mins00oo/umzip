package com.ssafy.umzip.domain.trade.controller;

import com.ssafy.umzip.domain.trade.service.BoardTradeService;
import com.ssafy.umzip.global.util.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/trade-items")
@RequiredArgsConstructor
@Slf4j
public class BoardTradeController {

    private final BoardTradeService service;
    private final JwtTokenProvider jwtTokenProvider;


}
