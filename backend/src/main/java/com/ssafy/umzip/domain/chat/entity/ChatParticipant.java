package com.ssafy.umzip.domain.chat.entity;

import com.ssafy.umzip.domain.member.entity.Member;
import com.ssafy.umzip.global.common.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatParticipant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_participant_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    @Column(name = "chat_participant_role")
    private String role;

    @Column(name = "chat_participant_activated")
    private boolean activated;

    @Builder
    public ChatParticipant(Member member, ChatRoom chatRoom, String role) {
        this.member = member;
        this.chatRoom = chatRoom;
        this.role = role;
        this.activated = true;
    }

    public void leaveChatRoom() {
        this.activated = false;
    }
}
