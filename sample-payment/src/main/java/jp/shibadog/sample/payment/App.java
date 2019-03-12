package jp.shibadog.sample.payment;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;


/**
 * Hello world!
 *
 */
public class App {

    private static String topicName = "first-app";
    public static void main(String[] args) {
        
        Properties conf = new Properties();

        for (Entry<String, String> entry: System.getenv().entrySet())
            conf.put(entry.getKey(), entry.getValue());
        
        conf.computeIfAbsent("bootstrap.servers", p -> "localhost:9092");
        conf.computeIfAbsent("group.id", p -> "FirstAppConsumerGroup");
        conf.computeIfAbsent("enable.auto.commit", p -> "false");
        conf.computeIfAbsent("key.deserializer", p -> "org.apache.kafka.common.serialization.IntegerDeserializer");
        conf.computeIfAbsent("value.deserializer", p -> "org.apache.kafka.common.serialization.StringDeserializer");

        try(Consumer<Integer, String> consumer = new KafkaConsumer<>(conf)) {
            List<String> topicList = new ArrayList<>(1);
            topicList.add(topicName);
            consumer.subscribe(topicList);

            for(int count= 0; count < 300; count++) {
                ConsumerRecords<Integer, String> records = consumer.poll(Duration.ofSeconds(1L));
            
                for (ConsumerRecord<Integer, String> record: records) {
                    String msgString = String.format("key:%d, value:%s", record.key(), record.value());
                    System.out.println(msgString);

                    TopicPartition tp = new TopicPartition(record.topic(), record.partition());
                    OffsetAndMetadata oam = new OffsetAndMetadata(record.offset() + 1);
                    Map<TopicPartition, OffsetAndMetadata> commitInfo = Collections.singletonMap(tp, oam);
                    consumer.commitSync(commitInfo);
                }
                
                try {
                    TimeUnit.SECONDS.sleep(1L);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
}
