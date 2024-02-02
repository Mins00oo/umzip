package com.ssafy.umzip.domain.chat.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.umzip.domain.chat.dto.ChatRoomListResponseDto;
import com.ssafy.umzip.domain.chat.entity.ChatMessage;
import com.ssafy.umzip.domain.chat.entity.ChatRoomStatus;
import com.ssafy.umzip.domain.chat.entity.QChatParticipant;
import com.ssafy.umzip.domain.chat.entity.QChatRoom;
import com.ssafy.umzip.domain.member.entity.QMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomChatParticipantRepositoryImpl implements CustomChatParticipantRepository {
    private final JPAQueryFactory queryFactory;
    private final MongoTemplate mongoTemplate;

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
                                        cp2.role.eq(role),
                                        cp2.status.eq(ChatRoomStatus.TALK))
                ))
                .where(cp.member.id.ne(memberId))
                .orderBy(cr.createDt.desc())
                .fetch();
    }

    @Override
    public List<ChatMessage> findRecentMessagesByChatRoomIds(List<Long> chatRoomIds) {
        for (Long id : chatRoomIds) {
            System.out.println("id = " + id);
        }
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("chatRoomId").in(chatRoomIds)),
                Aggregation.sort(Sort.Direction.DESC, "updateDt"),
                Aggregation.group("chatRoomId").first("$$ROOT").as("recentMessage"),
                Aggregation.project() // 필드 매핑 조정
                        .andExpression("recentMessage.chatRoomId").as("chatRoomId")
                        .andExpression("recentMessage.id").as("id")
                        .andExpression("recentMessage.senderId").as("senderId")
                        .andExpression("recentMessage.senderName").as("senderName")
                        .andExpression("recentMessage.content").as("content")
                        .andExpression("recentMessage.createDt").as("createDt")
                        .andExpression("recentMessage.updateDt").as("updateDt")
        );

        log.info(aggregation.toString());

        AggregationResults<ChatMessage> results = mongoTemplate.aggregate(
                aggregation,
                "chatMessage",
                ChatMessage.class
        );

        return results.getMappedResults();
    }
}
