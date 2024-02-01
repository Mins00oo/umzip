package com.ssafy.umzip.domain.trade.repository;

import com.ssafy.umzip.domain.trade.entity.BoardTradeActive;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardTradeActiveRepository extends JpaRepository<BoardTradeActive, Long> {

    Optional<BoardTradeActive> findByMemberIdAndBoardTradeId(Long memberId, Long boardTradeId);

}
