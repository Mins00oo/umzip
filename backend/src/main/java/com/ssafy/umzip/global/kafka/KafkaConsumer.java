package com.ssafy.umzip.global.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumer {
    private final SimpMessagingTemplate messagingTemplate;

    public KafkaConsumer(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @KafkaListener(topicPattern = "chat-messages-.*", groupId = "#{T(java.util.UUID).randomUUID().toString()}")
    public void listen(String message) {
        String chatRoomId = extractChatRoomIdFromMessage(message);

        messagingTemplate.convertAndSend("/topic/chatroom/" + chatRoomId, message);
    }

    private String extractChatRoomIdFromMessage(String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(message);
            return jsonNode.get("chatRoomId").asText();
        } catch (JsonProcessingException e) {
            log.error("Error parsing JSON message", e);
            return null;
        }
    }
}
