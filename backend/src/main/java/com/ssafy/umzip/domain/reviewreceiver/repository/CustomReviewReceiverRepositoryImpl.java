package com.ssafy.umzip.domain.reviewreceiver.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.umzip.domain.company.dto.CompanyReviewListResponse;
import com.ssafy.umzip.domain.member.dto.MemberLoginRequestDto;
import com.ssafy.umzip.domain.member.entity.QMember;
import com.ssafy.umzip.domain.review.entity.QReview;
import com.ssafy.umzip.domain.review.entity.Review;
import com.ssafy.umzip.domain.reviewreceiver.entity.QReviewReceiver;
import com.ssafy.umzip.domain.reviewreceiver.entity.ReviewReceiver;
import com.ssafy.umzip.domain.reviewtag.entity.QReviewTag;
import com.ssafy.umzip.domain.reviewtag.entity.ReviewTag;
import com.ssafy.umzip.domain.tag.entity.QTag;
import com.ssafy.umzip.global.common.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
        QMember memberAlias = new QMember("memberAlias");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


//        List<Review> reviews = queryFactory
//                .select(review)
//                .from(reviewReceiver)
//                .join(reviewReceiver.review, review)
//                .join(review.reviewTags, reviewTag).fetchJoin()
//                .join(reviewTag.tag, tag).fetchJoin()
//                .where(reviewReceiver.member.id.eq(memberId),
//                        reviewReceiver.receiverRole.eq(role))
//                .distinct()
//                .fetch();
//
//
//
//        return reviews.stream()
//                .map(reviewEntity -> {
//                    List<String> tagNames = reviewEntity.getReviewTags().stream()
//                            .map(reviewTagEntity -> reviewTagEntity.getTag().getTagName())
//                            .collect(Collectors.toList());
//                    return new CompanyReviewListResponse(
//                            reviewEntity.getId(),
//                            reviewEntity.getMember().getName(),
//                            reviewEntity.getMember().getImageUrl(),
//                            reviewEntity.getContent(),
//                            reviewEntity.getCreateDt().format(formatter),
//                            reviewEntity.getScore(),
//                            tagNames
//                    );
//                })
//                .collect(Collectors.toList());
        System.out.println(" ============start========= ");

        List<String> tagList = new ArrayList<>();

        List<CompanyReviewListResponse> responses = queryFactory
                .select(Projections.constructor(
                        CompanyReviewListResponse.class,
                        review.id.as("reviewId"),
                        review.member.name.as("writerName"),
                        review.member.imageUrl.as("writerProfileImage"),
                        review.content.as("reviewContent"),
                        review.score.as("score")
                ))
                .from(reviewReceiver)
                .join(reviewReceiver.review, review)
                .join(review.member, memberAlias)
                .where(reviewReceiver.receiverRole.eq(role),
                        reviewReceiver.member.id.eq(memberId))
                .fetch();

        for (CompanyReviewListResponse response : responses) {
            System.out.println("response = " + response);
        }

        System.out.println(" ======================= ");
//
//        System.out.println(" =========test ========= ");
//        List<ReviewReceiver> reviewReceivers = queryFactory
//                .select(reviewReceiver)
//                .from(reviewReceiver)
//                .join(reviewReceiver.review, review).fetchJoin()
//                .join(review.member, memberAlias).fetchJoin()
//                .where(reviewReceiver.receiverRole.eq(role),
//                        reviewReceiver.member.id.eq(memberId))
//                .distinct()
//                .fetch();
//
//        List<Review> reviewList = reviewReceivers.stream()
//                .map(ReviewReceiver::getReview)
//                .toList();
//
//        List<ReviewTag> tagList = queryFactory
//                .select(reviewTag)
//                .from(reviewTag)
//                .join(reviewTag.tag, tag).fetchJoin()
//                .join(reviewTag.review, review).fetchJoin()
//                .fetch();
//
//        Map<Long, List<String>> tagNameByReviewId = tagList.stream()
//                .collect(Collectors.groupingBy(
//                        tags -> tags.getReview().getId(),
//                        Collectors.mapping(tags -> tags.getTag().getTagName(), Collectors.toList())
//                ));
//
//        return reviewList.stream()
//                .map(reviewEntity -> {
//                    List<String> tagNameList = tagNameByReviewId.get(reviewEntity.getId()); // 순회하면서 id별 tagList
//                    return CompanyReviewListResponse.builder()
//                            .reviewId(reviewEntity.getId())
//                            .writerName(reviewEntity.getMember().getName())
//                            .writerProfileImage(reviewEntity.getMember().getImageUrl())
//                            .content(reviewEntity.getContent())
//                            .createDt(reviewEntity.getCreateDt().format(formatter))
//                            .score(reviewEntity.getScore())
//                            .tagList(tagNameList)
//                            .build();
//                })
//                .collect(Collectors.toList());

        return null;
    }
}
