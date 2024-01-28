package com.ssafy.umzip.domain.reviewreceiver.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.umzip.domain.company.dto.CompanyReviewListResponse;
import com.ssafy.umzip.domain.member.entity.QMember;
import com.ssafy.umzip.domain.review.entity.QReview;
import com.ssafy.umzip.domain.reviewreceiver.entity.QReviewReceiver;
import com.ssafy.umzip.domain.reviewtag.entity.QReviewTag;
import com.ssafy.umzip.domain.tag.entity.QTag;
import com.ssafy.umzip.global.common.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
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
        QMember member = QMember.member;

        List<CompanyReviewListResponse> responses = queryFactory
                .select(Projections.constructor(
                        CompanyReviewListResponse.class,
                        review.id.as("reviewId"),
                        review.member.name.as("writerName"),
                        review.member.imageUrl.as("writerProfileImage"),
                        review.content.as("reviewContent"),
                        review.createDt.as("createDt"),
                        review.score.as("score")
                ))
                .from(reviewReceiver)
                .join(reviewReceiver.review, review)
                .join(review.member, member)
                .where(reviewReceiver.receiverRole.eq(role),
                        reviewReceiver.member.id.eq(memberId))
                .fetch();

        List<Tuple> list = queryFactory
                .select(reviewTag.review.id, reviewTag.tag.tagName)
                .from(reviewTag)
                .join(reviewTag.review, review)
                .join(reviewTag.tag, tag)
                .fetch();


        Map<Long, List<String>> reviewIdToTagNames = list.stream()
                .collect(Collectors.groupingBy(
                        tuple -> tuple.get(reviewTag.review.id),
                        Collectors.mapping(tuple -> tuple.get(reviewTag.tag.tagName), Collectors.toList())
                ));

        responses.forEach(response -> {
            List<String> tagNameList = reviewIdToTagNames.get(response.getReviewId());
            response.setTagList(tagNameList);
        });

        return responses;
    }
}
