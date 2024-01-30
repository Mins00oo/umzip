package com.ssafy.umzip.domain.help.repository;

import com.ssafy.umzip.domain.help.entity.BoardHelp;
import com.ssafy.umzip.domain.help.entity.BoardHelpComment;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<BoardHelpComment, Long> {

    @Query("select count(b.boardHelp.id) as cnt " +
            "from BoardHelpComment b " +
            " where b.boardHelp.id = :boardId " +
            "group by b.boardHelp.id")
    Long countAllByBoardIdGroupBy(@Param("boardId") Long boardId);

    List<BoardHelpComment> findAllByBoardHelpId(Long boardHelpId);
}
