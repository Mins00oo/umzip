package com.ssafy.umzip.domain.chat.repository;

import com.ssafy.umzip.domain.chat.entity.ChatParticipant;
import com.ssafy.umzip.domain.chat.entity.ChatRoom;
import com.ssafy.umzip.domain.member.entity.Member;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatParticipantRepository extends JpaRepository<ChatParticipant, Long> {
    @Query("select cp.chatRoom FROM ChatParticipant cp where cp.member.id=:memberId AND " +
            "cp.role=:role")
    List<ChatRoom> findAllByMemberIdAndRole(@Param("memberId") Long memberId,@Param("role") String role);
}
