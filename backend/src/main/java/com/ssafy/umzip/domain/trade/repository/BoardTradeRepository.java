package com.ssafy.umzip.domain.trade.repository;

import com.ssafy.umzip.domain.trade.entity.BoardTrade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardTradeRepository extends JpaRepository<BoardTrade, Long> {

    Optional<BoardTrade> findByIdAndMemberId(Long boardId, Long memberId);
}

