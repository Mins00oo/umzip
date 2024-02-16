package com.ssafy.umzip.domain.trade.repository;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import com.ssafy.umzip.domain.trade.dto.ListDto;
import com.ssafy.umzip.domain.trade.dto.ProfileListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssafy.umzip.domain.trade.entity.QBoardTrade.boardTrade;
import static com.ssafy.umzip.domain.trade.entity.QBoardTradeImage.boardTradeImage;
import static com.ssafy.umzip.domain.trade.entity.QBoardTradeActive.boardTradeActive;

@RequiredArgsConstructor
@Repository
public class BoardTradeCustomRepositoryImpl implements BoardTradeCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ListDto> findBoardMatchingImageList(String title, int sigungu, Long codeSmallId, Pageable pageable) {

        List<ListDto> resultList = queryFactory.select(
                        Projections.constructor(
                                ListDto.class,
                                boardTrade.id.min().as("boardId"),
                                boardTrade.title.min().as("title"),
                                boardTrade.address.min().as("address"),
                                boardTrade.price.min().as("price"),
                                boardTrade.readCnt.min().as("readCnt"),
                                boardTrade.codeSmall.id.min().as("codeSmallId"),
                                boardTrade.codeSmall.name.min().as("codeSmallName"),
                                ExpressionUtils.as(queryFactory
                                        .select(boardTradeImage.path.min())
                                        .from(boardTradeImage)
                                        .where(boardTrade.id.eq(boardTradeImage.boardTrade.id))
                                        .groupBy(boardTradeImage.boardTrade.id), "thumbnailPath")
                        ))
                .from(boardTrade)
                .leftJoin(boardTrade.images, boardTradeImage)
                .on(boardTrade.id.eq(boardTradeImage.boardTrade.id))
                .where( (boardTrade.title.contains(title).and(boardTrade.codeSmall.id.eq(codeSmallId)).and( (boardTrade.isDirect.isTrue().and(boardTrade.sigungu.eq(sigungu))) ))
                        .or(boardTrade.title.contains(title).and(boardTrade.codeSmall.id.eq(codeSmallId)).and(boardTrade.isDirect.isFalse() ))
                )
                .groupBy(boardTrade.id)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(boardTrade.id.desc())
                .fetch();

        return resultList;

    }

    @Override
    public Page<ProfileListDto> findProfileSellMatchingImageList(Long memberId, Pageable pageable) {

        List<Long> list = queryFactory.select(boardTrade.count())
                .from(boardTrade)
                .where(boardTrade.id.in(queryFactory.select(boardTrade.id)
                        .from(boardTradeActive)
                        .where(boardTradeActive.member.id.eq(memberId))))
                .fetch();

        int size = list.size();

        Long totalSize = queryFactory.select(boardTrade.count().subtract(size))
                .from(boardTrade)
                .where(boardTrade.member.id.eq(memberId))
                        .fetchOne();
        System.out.println("totalSize: " + totalSize);

        if (totalSize < 0) {
            totalSize = 0L;
        }

        List<ProfileListDto> resultList = queryFactory.select(
                Projections.constructor(
                        ProfileListDto.class,
                        boardTrade.id.min().as("boardId"),
                        boardTrade.title.min().as("title"),
                        boardTrade.codeSmall.id.min().as("codeSmallId"),
                        boardTrade.codeSmall.name.min().as("codeSmallName"),
                        boardTrade.price.min().as("price"),
                        ExpressionUtils.as(queryFactory
                                .select(boardTradeImage.path.min())
                                .from(boardTradeImage)
                                .where(boardTrade.id.eq(boardTradeImage.boardTrade.id))
                                .groupBy(boardTradeImage.boardTrade.id), "thumbnailPath")
                )
                ).from(boardTrade)
                .leftJoin(boardTrade.images, boardTradeImage)
                .on(boardTrade.id.eq(boardTradeImage.boardTrade.id))
                .where(boardTrade.id
                        .notIn( queryFactory
                                .select(boardTradeActive.boardTrade.id)
                                .from(boardTradeActive))
                        .and(boardTrade.member.id.eq(memberId))
                )
                .groupBy(boardTrade.id)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(boardTrade.id.desc())
                .fetch();

        return new PageImpl<>(resultList, pageable, totalSize);
    }

    @Override
    public Page<ProfileListDto> findProfileBuyMatchingImageList(Long memberId, Pageable pageable) {

        Long totalSize = queryFactory.select(boardTradeActive.count())
                .from(boardTradeActive)
                .where(boardTradeActive.member.id.eq(memberId)).fetchOne();

        List<ProfileListDto> resultList = queryFactory.select(
                        Projections.constructor(
                                ProfileListDto.class,
                                boardTrade.id.min().as("boardId"),
                                boardTrade.title.min().as("title"),
                                Expressions.asNumber(303L).as("codeSmallId"),
                                Expressions.asString("구매완료").as("codeSmallName"),
                                boardTrade.price.min().as("price"),
                                ExpressionUtils.as(queryFactory
                                        .select(boardTradeImage.path.min())
                                        .from(boardTradeImage)
                                        .where(boardTrade.id.eq(boardTradeImage.boardTrade.id))
                                        .groupBy(boardTradeImage.boardTrade.id), "thumbnailPath")
                        )
                ).from(boardTrade)
                .leftJoin(boardTrade.images, boardTradeImage).on(boardTrade.id.eq(boardTradeImage.boardTrade.id))
                .leftJoin(boardTradeActive).on(boardTrade.id.eq(boardTradeActive.boardTrade.id))
                .where(boardTradeActive.member.id.eq(memberId))
                .groupBy(boardTrade.id)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(boardTrade.id.desc())
                .fetch();

        return new PageImpl<>(resultList, pageable, totalSize);
    }
}
