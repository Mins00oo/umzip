package com.ssafy.umzip.domain.review.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.umzip.domain.member.entity.Member;
import com.ssafy.umzip.domain.member.entity.QMember;
import com.ssafy.umzip.domain.review.dto.MyReceiveReviewRequest;
import com.ssafy.umzip.domain.review.dto.MyReceiveReviewResponse;
import com.ssafy.umzip.domain.review.entity.QReview;
import com.ssafy.umzip.domain.review.entity.Review;
import com.ssafy.umzip.domain.reviewreceiver.entity.QReviewReceiver;
import com.ssafy.umzip.domain.reviewreceiver.entity.ReviewReceiver;
import com.ssafy.umzip.domain.reviewtag.entity.ReviewTag;
import com.ssafy.umzip.domain.reviewtag.repository.ReviewTagRepository;
import com.ssafy.umzip.domain.tag.repository.TagRepository;
import com.ssafy.umzip.global.common.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@RequiredArgsConstructor
@Slf4j
public class CustomReviewRepositoryImpl implements CustomReviewRepository {
    private final JPAQueryFactory queryFactory;
    private final TagRepository tagRepository; // 변경된 부분
    private final ReviewTagRepository reviewTagRepository;

    /*
        -- 유저 ??에게 쓰인 모든 리뷰
        SELECT r.review_id, m.member_name, m.member_image_url,
               rr.receiver_role, r.review_content, r.review_score, r.create_dt
        FROM review_receiver rr
        JOIN review r ON r.review_id = rr.review_id
        JOIN member m ON m.member_id = r.member_id
        WHERE rr.member_id = 1
        AND rr.receiver_role = "USER"
        ORDER BY r.create_dt desc
        limit 2 offset 0;
         */
    @Override
    public List<MyReceiveReviewResponse> findReviewsForUser(MyReceiveReviewRequest request) {

        QReviewReceiver rr = QReviewReceiver.reviewReceiver;
        QReview r = QReview.review;
        QMember m = QMember.member;

        List<MyReceiveReviewResponse> reviewList;
        reviewList = queryFactory
                .select(Projections.fields
                        (MyReceiveReviewResponse.class,
                        r.id,
                        m.name,
                        m.imageUrl,
                        rr.receiverRole,
                        r.content,
                        r.score,
                        r.createDt)
                )
                .from(rr)
                .join(rr.review, r)
                .join(r.member, m)
                .where(
                        rr.member.id.eq(request.getMemberId())
                                .and(rr.receiverRole.eq(Role.valueOf(request.getRole())))
                )
                .orderBy(r.createDt.desc())
                .offset(request.getOffset())
                .limit(request.getLimit())
                .fetch();

        // 태그 정보 획득
        for (int i = 0; i < reviewList.size(); i++) {
            List<ReviewTag> tags = reviewTagRepository.findByReview_Id(reviewList.get(i).getId()).get();
            List<String> tagNames = new ArrayList<>();
            for (int j = 0; j < tags.size(); j++) {
                tagNames.add(tags.get(j).getTag().getTagName());
            }
            reviewList.get(i).setTag(tagNames);
        }
        return reviewList;
    }

    /*
        -- 유저 ??가 쓴 리뷰 정보
        SELECT r.review_id,
               rr.member_id,
               COALESCE(c.company_name, m.member_name) AS company_or_member_name,
               COALESCE(c.company_image_url, m.member_image_url) AS company_or_member_url,
               rr.receiver_role,
               r.review_content,
               r.review_score,
               r.create_dt
        FROM review r
        JOIN review_receiver rr ON r.review_id = rr.review_id
        LEFT JOIN company c ON rr.member_id = c.member_id AND c.company_role = rr.receiver_role
        LEFT JOIN member m ON rr.member_id = m.member_id AND c.company_role IS NULL
        WHERE r.member_id = 2
        ORDER BY r.create_dt desc
        limit 2 offset 0;
    * */

}
