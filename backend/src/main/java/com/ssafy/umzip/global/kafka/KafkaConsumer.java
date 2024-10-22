package com.ssafy.umzip.global.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumer {
    @KafkaListener(topicPattern = "chat-messages-.*", groupId = "#{T(java.util.UUID).randomUUID().toString()}")
    public void listen(String message) {
        log.info("Received message from Kafka: {}", message);
    }

}
