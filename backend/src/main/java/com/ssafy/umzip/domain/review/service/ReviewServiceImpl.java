package com.ssafy.umzip.domain.review.service;

import com.ssafy.umzip.domain.member.entity.Member;
import com.ssafy.umzip.domain.member.repository.MemberRepository;
import com.ssafy.umzip.domain.review.dto.AllReviewRequest;
import com.ssafy.umzip.domain.review.dto.CreateReviewRequest;
import com.ssafy.umzip.domain.review.dto.MyReceiveReviewRequest;
import com.ssafy.umzip.domain.review.dto.MyReceiveReviewResponse;
import com.ssafy.umzip.domain.review.entity.Review;
import com.ssafy.umzip.domain.review.repository.CustomReviewRepositoryImpl;
import com.ssafy.umzip.domain.review.repository.ReviewRepository;
import com.ssafy.umzip.domain.reviewreceiver.dto.TopTagListRequest;
import com.ssafy.umzip.domain.reviewreceiver.entity.ReviewReceiver;
import com.ssafy.umzip.domain.reviewreceiver.repository.CustomReviewReceiverRepositoryImpl;
import com.ssafy.umzip.domain.reviewreceiver.repository.ReviewReceiverRepository;
import com.ssafy.umzip.domain.reviewtag.entity.ReviewTag;
import com.ssafy.umzip.domain.reviewtag.repository.ReviewTagRepository;
import com.ssafy.umzip.domain.tag.entity.Tag;
import com.ssafy.umzip.domain.tag.repository.TagRepository;
import com.ssafy.umzip.global.common.BaseResponse;
import com.ssafy.umzip.global.common.Role;
import com.ssafy.umzip.global.common.StatusCode;
import com.ssafy.umzip.global.exception.BaseException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewReceiverRepository reviewReceiverRepository;
    private final ReviewTagRepository reviewTagRepository;
    private final MemberRepository memberRepository;
    private final TagRepository tagRepository;
    private final CustomReviewRepositoryImpl customReviewRepository;

    /*
    * 리뷰작성
    * */
    @Override
    @Transactional
    public ResponseEntity<Object> creaetReviwe(CreateReviewRequest createReviewRequest) {

        // 1) 리뷰 엔티티 저장
        Member reviewer = memberRepository.findById(createReviewRequest.getFrom())
                .orElseThrow(() -> new BaseException(StatusCode.NOT_EXIST_MEMBER));
        Review savedReview = reviewRepository.save(
                Review.builder()
                        .member(reviewer)
                        .score(createReviewRequest.getScore())
                        .content(createReviewRequest.getComment())
                        .build()
        );

        // 2) 인물 매핑테이블 저장
        Member receiver = memberRepository.findById(createReviewRequest.getTo())
                .orElseThrow(() -> new BaseException(StatusCode.NOT_EXIST_MEMBER));
        reviewReceiverRepository.save(
                ReviewReceiver.builder()
                        .role(createReviewRequest.getRole())
                        .member(receiver)
                        .review(savedReview)
                        .build()
        );

        // 3) 태그 매핑테이블 저장
        List<ReviewTag> reviewTags = createReviewRequest.getTag().stream()
                .map(tagId -> {
                    Tag tag = tagRepository.findById(tagId)
                            .orElseThrow(() -> new BaseException(StatusCode.NOT_EXIST_TAG));
                    return ReviewTag.builder()
                            .review(savedReview)
                            .tag(tag)
                            .build();
                })
                .collect(Collectors.toList());
        reviewTagRepository.saveAll(reviewTags);

        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(StatusCode.SUCCESS));
    }
    /*
     * 전체 리뷰 정보 반환 with pagination
     * */
    @Override
    public ResponseEntity<List<MyReceiveReviewResponse>> myReceiveReviewRequest(MyReceiveReviewRequest myReceiveReviewRequest) {
        Pageable pageable = PageRequest.of(myReceiveReviewRequest.getOffset(), myReceiveReviewRequest.getLimit());
        Optional<List<MyReceiveReviewResponse>> reviewResponses = reviewRepository.findReviewDetailsByMemberIdAndRole(myReceiveReviewRequest.getMemberId(), Role.valueOf(myReceiveReviewRequest.getRole()), pageable);

        if (reviewResponses.isPresent()) {

            List<MyReceiveReviewResponse> reviewList = reviewResponses.get();

            // 태그 정보 획득
            for (int i = 0; i < reviewList.size(); i++) {
                List<ReviewTag> tags = reviewTagRepository.findByReview_Id(reviewList.get(i).getId()).get();
                List<String> tagNames = new ArrayList<>();
                for (int j = 0; j < tags.size(); j++) {
                    tagNames.add(tags.get(j).getTag().getTagName());
                }
                reviewList.get(i).setTag(tagNames);
            }
            return ResponseEntity.status(HttpStatus.OK).body(reviewResponses.get());
        } else {
            return ResponseEntity.notFound().build();
        }
//        return ResponseEntity.status(HttpStatus.CREATED).body(customReviewRepository.findReviewsForUser(myReceiveReviewRequest));
    }

}
