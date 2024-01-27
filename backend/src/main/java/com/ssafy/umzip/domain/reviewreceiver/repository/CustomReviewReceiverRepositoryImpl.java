package com.ssafy.umzip.domain.reviewreceiver.repository;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
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
        QReviewReceiver reviewReceiver = QReviewReceiver.reviewReceiver;
        QReview review = QReview.review;
        QMember member = QMember.member;
        QTag tag = QTag.tag;
        QReviewTag reviewTag = QReviewTag.reviewTag;

        List<CompanyReviewListResponse> results = queryFactory
                .select(Projections.constructor(
                        CompanyReviewListResponse.class,
                        review.id,
                        member.name,
                        member.imageUrl,
                        review.content,
                        review.createDt,
                        review.score
                ))
                .from(reviewReceiver)
                .join(reviewReceiver.review, review)
                .join(review.member, member)
                .where(reviewReceiver.receiverRole.eq(role))
                .where(reviewReceiver.member.id.eq(memberId))
                .orderBy(review.createDt.desc())
                .limit(limit)
                .fetch();

        for (CompanyReviewListResponse response : results) {
            List<String> tagList = queryFactory
                    .select(tag.tagName)
                    .from(reviewTag)
                    .join(reviewTag.tag, tag)
                    .where(reviewTag.review.id.eq(response.getReviewId()))
                    .fetch();

            response.setTagList(tagList);
        }
        return results;
    }
}
