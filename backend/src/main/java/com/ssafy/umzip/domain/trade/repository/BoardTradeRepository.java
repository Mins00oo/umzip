package com.ssafy.umzip.domain.trade.repository;

import com.ssafy.umzip.domain.trade.entity.BoardTrade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardTradeRepository extends JpaRepository<BoardTrade, Long> {

    @Query("select b from BoardTrade b " +
            "where b.id not in ( select ba.boardTrade.id from BoardTradeActive ba ) " +
            "and b.member.id = :memberId ")
    Page<BoardTrade> findAllBySellMemberId(Long memberId, Pageable pageable);

    @Query("select b from BoardTrade b " +
            "where b.id in ( select ba.boardTrade.id from BoardTradeActive ba ) " +
            "and b.member.id = :memberId ")
    Page<BoardTrade> findAllByBuyMemberId(Long memberId, Pageable pageable);
}

