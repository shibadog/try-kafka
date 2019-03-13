package jp.shibadog.sample.front.domain.service;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService {

    @Autowired
    private KafkaTemplate<String, Map<String, Object>> template;

    public String purchase(Map<String, Object> body) {
        String acceptKey = UUID.randomUUID().toString();
        template.send("purchase", acceptKey, Collections.singletonMap("acceptKey", acceptKey));
        template.flush();
        return acceptKey;
    }
}
