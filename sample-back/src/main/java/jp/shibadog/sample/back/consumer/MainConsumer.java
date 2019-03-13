package jp.shibadog.sample.back.consumer;

import java.util.Map;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MainConsumer {

    @KafkaListener(id = "fooGroup", topics = "purchase")
    public void listen(Map<String, Object> foo) {
        log.info("Received: " + foo);
    }
}
