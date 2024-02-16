package com.ssafy.umzip.domain.tag.controller;

import com.ssafy.umzip.domain.review.service.ReviewService;
import com.ssafy.umzip.domain.tag.dto.TagListByLargeCodeResponce;
import com.ssafy.umzip.domain.tag.service.TagService;
import com.ssafy.umzip.global.common.BaseResponse;
import com.ssafy.umzip.global.common.StatusCode;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
@Slf4j
public class TagController {
    private final TagService tagService;

    @GetMapping( value = "/tagType")
    public ResponseEntity<List<TagListByLargeCodeResponce>> receiveTagType(@RequestParam String role) {
        return ResponseEntity.status(HttpStatus.OK).body(tagService.findByCodeLargeName(role));
    }

}
