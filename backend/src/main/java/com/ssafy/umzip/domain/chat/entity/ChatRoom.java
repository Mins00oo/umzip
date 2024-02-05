package com.ssafy.umzip.domain.chat.entity;

import com.ssafy.umzip.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id")
    private Long id;

    @Column(name = "chat_room_type")
    @Enumerated(EnumType.STRING)
    private ChatRoomType roomType;

    @Column(name = "chat_room_trade_id")
    @Setter
    private Long tradeId;

    @Builder
    public ChatRoom(ChatRoomType roomType) {
        this.roomType = roomType;
    }
}
