package com.ssafy.umzip.domain.help.repository;

import com.ssafy.umzip.domain.help.entity.BoardHelp;
import com.ssafy.umzip.domain.help.entity.BoardHelpImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardHelpImageRepository extends JpaRepository<BoardHelpImage, Long> {

    @Query("select b from BoardHelpImage b where b.boardHelp.id = :boardId ")
    List<BoardHelpImage> findAllById(Long boardId);
}
