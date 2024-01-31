package com.ssafy.umzip.domain.chat.repository;

import com.ssafy.umzip.domain.chat.entity.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
}
