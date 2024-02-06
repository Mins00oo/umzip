package com.ssafy.umzip.domain.trade.repository;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import com.ssafy.umzip.domain.trade.dto.BoardTradeListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssafy.umzip.domain.trade.entity.QBoardTrade.boardTrade;
import static com.ssafy.umzip.domain.trade.entity.QBoardTradeImage.boardTradeImage;

@RequiredArgsConstructor
@Repository
public class BoardTradeCustomRepositoryImpl implements BoardTradeCustomRepository {

    private final JPAQueryFactory queryFactory;
    @Override
    public List<BoardTradeListDto> findBoardMatchingImageList(String title, int sigungu, Long codeSmallId, Pageable pageable) {

        List<BoardTradeListDto> list = queryFactory.select(
                        Projections.constructor(
                                BoardTradeListDto.class,
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

        return list;

    }
}
