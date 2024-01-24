package com.ssafy.umzip.domain.help.repository;

import com.ssafy.umzip.domain.help.entity.BoardHelp;
import com.ssafy.umzip.domain.help.entity.BoardHelpImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardHelpImageRepository extends JpaRepository<BoardHelpImage, Long> {
}
