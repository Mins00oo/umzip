package com.ssafy.umzip.domain.reviewtag.service;

import com.ssafy.umzip.domain.code.entity.CodeSmall;
import com.ssafy.umzip.domain.reviewtag.dto.TagIdByReviewIdResponse;
import com.ssafy.umzip.domain.reviewtag.entity.ReviewTag;
import com.ssafy.umzip.domain.reviewtag.repository.ReviewTagRepository;
import com.ssafy.umzip.domain.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
@Service
public class ReviewTagServiceImpl implements ReviewTagService{
    private final ReviewTagRepository reviewTagRepository;

    public ReviewTagServiceImpl(ReviewTagRepository reviewTagRepository) {
        this.reviewTagRepository = reviewTagRepository;
    }

    @Override
    public List<TagIdByReviewIdResponse> findMatchTag(long reviewId) {
        Optional<List<ReviewTag>> reviewTags = reviewTagRepository.findByReview_Id(reviewId);

        List<TagIdByReviewIdResponse> reviewList = new ArrayList<>();
        if(reviewTags.isPresent()) {
            List<ReviewTag> reviewTagList = reviewTags.get();
            for (int i = 0; i < reviewTagList.size(); i++) {
                reviewList.add(new TagIdByReviewIdResponse(reviewTagList.get(i).getTag().getTagId(), reviewTagList.get(i).getTag().getTagName()));
            }
        }
        else {
            return reviewList;
        }
        return reviewList;
    }
}
