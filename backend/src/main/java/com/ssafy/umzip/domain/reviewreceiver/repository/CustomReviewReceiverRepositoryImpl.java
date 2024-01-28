package com.ssafy.umzip.domain.reviewreceiver.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.umzip.domain.company.dto.CompanyReviewListResponse;
import com.ssafy.umzip.domain.review.entity.QReview;
import com.ssafy.umzip.domain.review.entity.Review;
import com.ssafy.umzip.domain.reviewreceiver.entity.QReviewReceiver;
import com.ssafy.umzip.domain.reviewtag.entity.QReviewTag;
import com.ssafy.umzip.domain.tag.entity.QTag;
import com.ssafy.umzip.global.common.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class CustomReviewReceiverRepositoryImpl implements CustomReviewReceiverRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<String> findTopTagsByMemberId(Long memberId, int limit, Role role) {
        QReviewReceiver reviewReceiver = QReviewReceiver.reviewReceiver;
        QReviewTag reviewTag = QReviewTag.reviewTag;
        QTag tag = QTag.tag;

        return queryFactory
                .select(tag.tagName)
                .from(reviewReceiver)
                .join(reviewTag).on(reviewReceiver.review.eq(reviewTag.review))
                .where(reviewReceiver.receiverRole.eq(role))
                .join(reviewTag.tag, tag)
                .where(reviewReceiver.member.id.eq(memberId))
                .groupBy(tag.tagId, tag.tagName)
                .orderBy(tag.tagId.count().desc())
                .limit(limit)
                .fetch();
    }

    @Override
    public List<CompanyReviewListResponse> findReviewByMemberIdAndRole(Long memberId, int limit, Role role) {
        QReview review = QReview.review;
        QReviewReceiver reviewReceiver = QReviewReceiver.reviewReceiver;
        QReviewTag reviewTag = QReviewTag.reviewTag;
        QTag tag = QTag.tag;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


        List<Review> reviews = queryFactory
                .select(review)
                .from(reviewReceiver)
                .join(reviewReceiver.review, review)
                .join(review.reviewTags, reviewTag).fetchJoin()
                .join(reviewTag.tag, tag).fetchJoin()
                .where(reviewReceiver.member.id.eq(memberId),
                        reviewReceiver.receiverRole.eq(role))
                .distinct()
                .fetch();

        return reviews.stream()
                .map(reviewEntity -> {
                    List<String> tagNames = reviewEntity.getReviewTags().stream()
                            .map(reviewTagEntity -> reviewTagEntity.getTag().getTagName())
                            .collect(Collectors.toList());
                    return new CompanyReviewListResponse(
                            reviewEntity.getId(),
                            reviewEntity.getMember().getName(),
                            reviewEntity.getMember().getImageUrl(),
                            reviewEntity.getContent(),
                            reviewEntity.getCreateDt().format(formatter),
                            reviewEntity.getScore(),
                            tagNames
                    );
                })
                .collect(Collectors.toList());
    }
}
