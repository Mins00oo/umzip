package com.ssafy.umzip.domain.help.repository;

import com.ssafy.umzip.domain.help.entity.BoardHelpComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<BoardHelpComment, Long> {
}
