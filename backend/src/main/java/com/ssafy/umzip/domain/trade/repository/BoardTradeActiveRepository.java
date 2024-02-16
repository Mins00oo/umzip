package com.ssafy.umzip.domain.trade.repository;

import com.ssafy.umzip.domain.trade.dto.CompleteBuyLogicDto;
import com.ssafy.umzip.domain.trade.entity.BoardTradeActive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BoardTradeActiveRepository extends JpaRepository<BoardTradeActive, Long> {

    Optional<BoardTradeActive> findByMemberIdAndBoardTradeId(Long memberId, Long boardTradeId);

    @Query("select new com.ssafy.umzip.domain.trade.dto.CompleteBuyLogicDto" +
            "(ba.boardTrade.id, ba.member.id, ba.isActive) " +
            "from BoardTradeActive ba " +
            "where ba.boardTrade.id = :boardTradeId " +
            "and ba.member.id = :buyerId ")
    List<CompleteBuyLogicDto> findAll(Long boardTradeId, Long buyerId);
}
