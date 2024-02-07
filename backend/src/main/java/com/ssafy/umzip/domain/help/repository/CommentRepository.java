package com.ssafy.umzip.domain.help.repository;

import com.ssafy.umzip.domain.help.entity.BoardHelpComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<BoardHelpComment, Long> {

    List<BoardHelpComment> findAllByBoardHelpId(Long boardHelpId);
}
