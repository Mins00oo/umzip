package com.ssafy.umzip.domain.chat.entity;

import com.ssafy.umzip.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    @Enumerated(EnumType.STRING)
    private ChatRoomStatus status;

    @Column(name = "chat_participant_last_read")
    private String messageId;

    @Builder
    public ChatParticipant(Member member, ChatRoom chatRoom, String role) {
        this.member = member;
        this.chatRoom = chatRoom;
        this.role = role;
        this.status = ChatRoomStatus.ENTER;
    }

    public void leaveChatRoom() {
        this.status = ChatRoomStatus.LEAVE;
    }

    public void talkWithChatRoom() {
        this.status = ChatRoomStatus.TALK;
    }
}
