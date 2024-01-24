package com.ssafy.umzip.domain.help.controller;

import com.ssafy.umzip.domain.code.entity.CodeSmall;
import com.ssafy.umzip.domain.help.entity.BoardHelp;
import com.ssafy.umzip.domain.help.service.BoardHelpService;
import com.ssafy.umzip.domain.help.service.BoardHelpServiceImpl;
import com.ssafy.umzip.global.common.BaseResponse;
import com.ssafy.umzip.global.common.StatusCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/helps")
@RequiredArgsConstructor
@Slf4j
public class BoardHelpController {

    private final BoardHelpService boardHelpService;

    @GetMapping()
    public ResponseEntity<Object> retrieveList(
            @RequestParam("code-small") int codeSmall,
            @PageableDefault(sort="id", direction = Sort.Direction.DESC) Pageable pageable) {

        // accessToken 이 있는지 판단한다. 없으면 로그인 창으로 이동하게끔 에러 코드를 보낸다.

        // accessToken 까서 시군구 데이터 service 로 넘겨야 한다.

        // service
        // boardHelpService.retrieveList()

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(StatusCode.SUCCESS));  // result에 boardList를 넘겨야 한다.
    }
}
