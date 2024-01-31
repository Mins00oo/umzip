package com.ssafy.umzip.domain.trade.repository;

import com.ssafy.umzip.domain.help.entity.BoardHelpImage;
import com.ssafy.umzip.domain.trade.entity.BoardTrade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardTradeRepository extends JpaRepository<BoardTrade, Long> {
}
