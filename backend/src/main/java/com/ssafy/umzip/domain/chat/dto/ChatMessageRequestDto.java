package com.ssafy.umzip.domain.chat.dto;

import com.ssafy.umzip.domain.chat.entity.ChatMessage;
import com.ssafy.umzip.domain.member.dto.MemberCreateRequestDto;
import com.ssafy.umzip.domain.member.entity.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ChatMessageRequestDto {
    private String content;

    private String type;

    public static ChatMessage toEntity(
            ChatMessageRequestDto requestDto,
            Member member,
            Long chatRoomId
    ) {
        return ChatMessage.builder()
                .content(requestDto.content)
                .chatRoomId(chatRoomId)
                .senderName(member.getName())
                .senderProfileImage(member.getImageUrl())
                .senderId(String.valueOf(member.getId()))
                .build();
    }

    public static ChatMessageResponseDto toResponseDto(
            String content,
            Member member,
            LocalDateTime sendTime
    ) {
        return ChatMessageResponseDto.builder()
                .content(content)
                .requesterId(member.getId())
                .senderId(String.valueOf(member.getId()))
                .sendTime(sendTime)
                .senderName(member.getName())
                .senderProfileImage(member.getImageUrl())
                .build();

    }

}
