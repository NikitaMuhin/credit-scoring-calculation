package com.example.creditscoring.kafka;

import com.example.creditscoring.model.CreditScore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;
import reactor.kafka.sender.SenderRecord;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class KafkaProducer {
    private static final Logger log = LoggerFactory.getLogger(KafkaProducer.class.getName());

    private final KafkaSender<Integer, String> sender;

    private ObjectMapper objectMapper = new ObjectMapper();

    public KafkaProducer(String bootstrapServers) {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, "sample-producer");
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        sender = KafkaSender.create(SenderOptions.create(properties));
    }

    public void sendMessages(String topic, Mono<CreditScore> creditScoreMono, CountDownLatch latch) throws InterruptedException {
        sender.send(creditScoreMono
                        .map(i -> SenderRecord.create(new ProducerRecord<>(topic, toBinary(i)), i.getId())))
                .doOnError(e -> log.error("Send failed", e))
                .subscribe(record -> {
                    RecordMetadata metadata = record.recordMetadata();
                    Instant timestamp = Instant.ofEpochMilli(metadata.timestamp());
                    System.out.printf("Message  sent successfully, topic-partition=%s-%d offset=%d timestamp=%s\n",
                            metadata.topic(),
                            metadata.partition(),
                            metadata.offset(),
                            timestamp);
                    latch.countDown();
                });
    }

    private String toBinary(Object object) {
        try {
            objectMapper.findAndRegisterModules();
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

}