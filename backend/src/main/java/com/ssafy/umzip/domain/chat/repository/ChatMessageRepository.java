package com.ssafy.umzip.domain.chat.repository;

import com.ssafy.umzip.domain.chat.entity.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String>{
    List<ChatMessage> findAllByChatRoomId(Long chatRoomId);

}
