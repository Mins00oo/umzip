package com.ssafy.umzip.global.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    // KafkaTemplate을 생성자로 주입받음
    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    // 메시지를 Kafka 토픽에 발행
    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }
}
