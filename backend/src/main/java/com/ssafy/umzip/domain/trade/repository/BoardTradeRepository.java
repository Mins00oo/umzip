package com.ssafy.umzip.domain.trade.repository;

import com.ssafy.umzip.domain.help.entity.BoardHelpImage;
import com.ssafy.umzip.domain.trade.dto.ListDto;
import com.ssafy.umzip.domain.trade.entity.BoardTrade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardTradeRepository extends JpaRepository<BoardTrade, Long> {

    @Query("select b from BoardTrade b " +
            "where (b.codeSmall.id = :codeSmallId and b.title like %:title% and (b.isDirect = true and b.sigungu = :sigungu)) " +
            "or (b.codeSmall.id = :codeSmallId and b.title like %:title% and b.isDirect = false)")
    Slice<BoardTrade> findBoardTradeList(@Param("title") String title, @Param("sigungu") int sigungu,
                                         @Param("codeSmallId") Long codeSmallId, Pageable pageable);

//    @Query("select new com.ssafy.umzip.domain.trade.dto.ListDto(b.boardId, b.boardTradeImageList, b.title, b.address, b.price, b.readCnt, b.codeSmall.getId(), b.codeSmall.getName()) " +
//            " from BoardTrade b LEFT JOIN BoardTradeImageList bt ON b.id = bt.boardTrade.getId() ")
//    Slice<ListDto> findTest();

    @Query("select b from BoardTrade b " +
            "where b.id not in ( select ba.boardTrade.id from BoardTradeActive ba ) " +
            "and b.member.id = :memberId ")
    Page<BoardTrade> findAllBySellMemberId(Long memberId, Pageable pageable);

    @Query("select b from BoardTrade b " +
            "where b.id in ( select ba.boardTrade.id from BoardTradeActive ba ) " +
            "and b.member.id = :memberId ")
    Page<BoardTrade> findAllByBuyMemberId(Long memberId, Pageable pageable);
}

