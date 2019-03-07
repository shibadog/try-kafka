package jp.shibadog.sample.producer;

import java.util.Properties;
import java.util.Map.Entry;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class App {

    private static String topicName = "first-app";
    public static void main(String[] args) {

        
        Properties conf = new Properties();

        for (Entry<String, String> entry: System.getenv().entrySet())
            conf.put(entry.getKey(), entry.getValue());

        conf.computeIfAbsent("bootstrap.servers", p -> "localhost:9092");
        conf.computeIfAbsent("key.serializer", p -> "org.apache.kafka.common.serialization.IntegerSerializer");
        conf.computeIfAbsent("value.serializer", p -> "org.apache.kafka.common.serialization.StringSerializer");

        try (Producer<Integer, String> producer = new KafkaProducer<>(conf)) {
            for (int i = 1; i <= 100; i++) {

                ProducerRecord<Integer, String> record = new ProducerRecord<>(topicName, i, String.valueOf(i));

                producer.send(record, (metadata, e) -> {
                    if (metadata != null) {
                        // 成功
                        String infoString = String.format("Success partition:%d, offset:%d",
                            metadata.partition(), metadata.offset());
                        System.out.println(infoString);
                    } else {
                        // 失敗
                        String infoString = String.format("Faild:%s", e.getMessage());
                        System.err.println(infoString);
                    }
                });
            }
        }
    }
}