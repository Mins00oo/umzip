package com.ssafy.umzip.domain.trade.repository;

import com.ssafy.umzip.domain.help.entity.BoardHelpImage;
import com.ssafy.umzip.domain.trade.entity.BoardTradeImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardTradeImageRepository extends JpaRepository<BoardTradeImage, Long> {
}
