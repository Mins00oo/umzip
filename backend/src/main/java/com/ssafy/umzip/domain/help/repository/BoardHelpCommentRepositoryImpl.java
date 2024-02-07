package com.ssafy.umzip.domain.help.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.umzip.domain.help.dto.ListHelpDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static com.ssafy.umzip.domain.help.entity.QBoardHelp.boardHelp;
import static com.ssafy.umzip.domain.help.entity.QBoardHelpComment.boardHelpComment;

@RequiredArgsConstructor
@Repository
public class BoardHelpCommentRepositoryImpl implements BoardHelpCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ListHelpDto> listBoardHelpAndCommentCount(String title, int sigungu, Long codeSmallId, Pageable pageable) {

        BooleanBuilder builder = new BooleanBuilder();

        // 401L이면 401, 403을 불러온다
        // 402L이면 402만 불러온다
        // 0이면 401, 402, 403 모두를 불러온다.
        if (Objects.equals(codeSmallId, 401L)) {
            builder.and(boardHelp.codeSmall.id.in(401L, 403L));
        } else if (Objects.equals(codeSmallId, 402L)) {
            builder.and(boardHelp.codeSmall.id.eq(402L));
        }

        builder.and(boardHelp.title.like("%" + title + "%"));
        builder.and(boardHelp.sigungu.eq(sigungu));

        List<ListHelpDto> resultList = queryFactory.select(
                Projections.constructor(
                        ListHelpDto.class,
                        boardHelp.id.as("id"),
                        boardHelp.member.name.as("writerName"),
                        boardHelp.title.as("title"),
                        boardHelp.codeSmall.id.as("codeSmallId"),
                        boardHelp.createDt.as("createDt"),
                        ExpressionUtils.as(queryFactory.select(boardHelpComment.count())
                                        .from(boardHelpComment)
                                        .where(boardHelpComment.boardHelp.id.eq(boardHelp.id))
                                , "commentCnt"),
                        boardHelp.readCnt.as("readCnt"),
                        boardHelp.point.as("rewardPoint"),
                        boardHelp.sigungu.as("sigungu"),
                        boardHelp.isAdopted.as("isAdopted")
                ))
                .from(boardHelp)
                .where(builder)
                .fetch();

        Long totalCount = queryFactory.select(boardHelp.count())
                .from(boardHelp)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(resultList, pageable, totalCount);
    }
}
