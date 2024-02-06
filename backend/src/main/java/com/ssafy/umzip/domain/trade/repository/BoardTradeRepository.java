package com.ssafy.umzip.domain.trade.repository;

import com.ssafy.umzip.domain.trade.entity.BoardTrade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardTradeRepository extends JpaRepository<BoardTrade, Long> {
}

