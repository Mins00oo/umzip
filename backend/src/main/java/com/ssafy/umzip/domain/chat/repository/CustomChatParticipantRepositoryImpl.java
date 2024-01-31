package com.ssafy.umzip.domain.chat.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.umzip.domain.chat.dto.ChatRoomListResponseDto;
import com.ssafy.umzip.domain.chat.entity.QChatParticipant;
import com.ssafy.umzip.domain.chat.entity.QChatRoom;
import com.ssafy.umzip.domain.member.entity.QMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomChatParticipantRepositoryImpl implements CustomChatParticipantRepository{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<ChatRoomListResponseDto> findChatRoomDetailsByMemberIdAndRole(Long memberId, String role) {
        QChatParticipant cp = QChatParticipant.chatParticipant;
        QChatParticipant cp2 = QChatParticipant.chatParticipant;
        QMember m = QMember.member;
        QChatRoom cr = QChatRoom.chatRoom;
        return queryFactory
                .select(Projections.constructor(
                        ChatRoomListResponseDto.class,
                        cp.chatRoom.id,
                        m.id,
                        m.imageUrl,
                        m.name,
                        cp.chatRoom.updateDt))
                .from(cp)
                .join(cp.member, m)
                .join(cp.chatRoom, cr)
                .where(cp.chatRoom.id.in(
                        JPAExpressions
                                .select(cp2.chatRoom.id)
                                .from(cp2)
                                .where(cp2.member.id.eq(memberId),
                                        cp2.role.eq(role))
                ))
                .where(cp.member.id.ne(memberId),
                        cp.role.eq(role))
                .orderBy(cr.createDt.desc())
                .fetch();
    }
}
