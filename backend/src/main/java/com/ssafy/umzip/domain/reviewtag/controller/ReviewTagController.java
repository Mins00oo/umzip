package com.ssafy.umzip.domain.reviewtag.controller;

import com.ssafy.umzip.domain.reviewtag.dto.TagIdByReviewIdResponse;
import com.ssafy.umzip.domain.reviewtag.service.ReviewTagService;
import com.ssafy.umzip.domain.tag.dto.TagListByLargeCodeResponce;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
@Slf4j
public class ReviewTagController {
    private final ReviewTagService reviewTagService;

    @GetMapping( value = "/review_tag")
    public ResponseEntity<List<TagIdByReviewIdResponse>> findMatchTagByReviewId(@RequestParam long reviewId) {
        return ResponseEntity.status(HttpStatus.OK).body(reviewTagService.findMatchTag(reviewId));
    }
}
