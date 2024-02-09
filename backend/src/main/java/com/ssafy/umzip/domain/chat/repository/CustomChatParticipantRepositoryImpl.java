package com.ssafy.umzip.domain.chat.repository;

import com.mongodb.BasicDBObject;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.umzip.domain.chat.dto.ChatRoomListResponseDto;
import com.ssafy.umzip.domain.chat.entity.ChatMessage;
import com.ssafy.umzip.domain.chat.entity.ChatRoomStatus;
import com.ssafy.umzip.domain.chat.entity.QChatParticipant;
import com.ssafy.umzip.domain.chat.entity.QChatRoom;
import com.ssafy.umzip.domain.company.entity.QCompany;
import com.ssafy.umzip.domain.member.entity.QMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
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
        QCompany c = QCompany.company;

        NumberExpression<Long> id = new CaseBuilder()
                .when(cp.role.in("CLEAN", "DELIVER")).then(c.id)
                .otherwise(m.id);

        StringExpression imageUrl = new CaseBuilder()
                .when(cp.role.in("CLEAN", "DELIVER")).then(c.imageUrl)
                .otherwise(m.imageUrl);

        StringExpression name = new CaseBuilder()
                .when(cp.role.in("CLEAN", "DELIVER")).then(c.name)
                .otherwise(m.name);

        return queryFactory
                .select(Projections.constructor(
                        ChatRoomListResponseDto.class,
                        cp.chatRoom.id,
                        id,
                        imageUrl,
                        name,
                        cp.chatRoom.updateDt))
                .from(cp)
                .join(cp.member, m)
                .leftJoin(c).on(cp.member.id.eq(c.member.id))
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
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("chatRoomId").in(chatRoomIds)),
                Aggregation.sort(Sort.Direction.DESC, "updateDt"),
                Aggregation.group("chatRoomId").first("$$ROOT").as("recentMessage"),
                Aggregation.project()
                        .andExpression("recentMessage.chatRoomId").as("chatRoomId")
                        .andExpression("recentMessage.id").as("id")
                        .andExpression("recentMessage.senderId").as("senderId")
                        .andExpression("recentMessage.senderName").as("senderName")
                        .andExpression("recentMessage.content").as("content")
                        .andExpression("recentMessage.createDt").as("createDt")
                        .andExpression("recentMessage.updateDt").as("updateDt")
        );
        AggregationResults<ChatMessage> results = mongoTemplate.aggregate(
                aggregation,
                "chatMessage",
                ChatMessage.class
        );

        return results.getMappedResults();
    }

    @Override
    public long getAllUnReadMessageCount(Long chatRoomId) {
        Criteria criteria = Criteria.where("chatRoomId").is(chatRoomId);
        AggregationOperation match = Aggregation.match(criteria);

        AggregationOperation unReadCount = Aggregation.count().as("allUnReadCount");
        Aggregation aggregation = Aggregation.newAggregation(match, unReadCount);

        AggregationResults<BasicDBObject> results = mongoTemplate.aggregate(
                aggregation,
                "chatMessage",
                BasicDBObject.class
        );
        BasicDBObject result = results.getUniqueMappedResult();
        return result.getLong("allUnReadCount");
    }

    @Override
    public long getNewMessageCount(Long chatRoomId, String lastReadMessageId) {
        ObjectId objectId = new ObjectId(lastReadMessageId);
        Criteria criteria = Criteria.where("chatRoomId").is(chatRoomId)
                .andOperator(Criteria.where("_id").gt(objectId));

        AggregationOperation match = Aggregation.match(criteria);

        AggregationOperation count = Aggregation.count().as("newMessagesCount");

        Aggregation aggregation = Aggregation.newAggregation(match, count);

        AggregationResults<BasicDBObject> results = mongoTemplate.aggregate(
                aggregation,
                "chatMessage",
                BasicDBObject.class
        );
        BasicDBObject result = results.getUniqueMappedResult();
        return result != null ? result.getLong("newMessagesCount") : 0;
    }
}
