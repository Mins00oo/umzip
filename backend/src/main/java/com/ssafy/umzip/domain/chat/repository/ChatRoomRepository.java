package com.ssafy.umzip.domain.chat.repository;

import com.ssafy.umzip.domain.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    @Query("SELECT cr FROM ChatRoom cr " +
            "WHERE cr.roomType = :role " +
            "AND EXISTS (SELECT cp FROM ChatParticipant cp WHERE cp.chatRoom.id = cr.id AND cp.member.id = :senderId) " +
            "AND EXISTS (SELECT cp FROM ChatParticipant cp WHERE cp.chatRoom.id = cr.id AND cp.member.id = :receiverId)")
    ChatRoom findExistingChatRoom(@Param("senderId") Long senderId,
                                  @Param("receiverId") Long receiverId,
                                  @Param("role") String role);
}
