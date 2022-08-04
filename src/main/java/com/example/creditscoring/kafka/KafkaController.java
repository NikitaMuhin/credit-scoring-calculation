package com.example.creditscoring.kafka;

import com.example.creditscoring.model.CreditScore;
import com.example.creditscoring.model.PersonalData;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.ReceiverRecord;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaController {

    private final KieContainer kieContainer;

    private final Flux<ReceiverRecord<String, String>> reactiveKafkaReceiver;

    @EventListener(ApplicationStartedEvent.class)
    public void onMessage() {
        reactiveKafkaReceiver
                .doOnNext(r -> createPersonalDataMono(r.value()))
                .doOnError(e -> log.error("KafkaFlux exception", e))
                .subscribe();

    }

    private void createPersonalDataMono(String personalDataString) {
        log.info(personalDataString);
        Mono<PersonalData> personalDataMono = Mono.just(fromBinary(personalDataString, PersonalData.class));
        personalDataMono.doOnNext(p -> log.info(p.getEmail())).subscribe();
        personalDataMono.flatMap(personalData -> {
            try {
                return calculationCreditScoring(personalData);
            } catch (InterruptedException e) {
                return Mono.error(new RuntimeException(e));
            }
        }).subscribe();
    }

    private Mono<CreditScore> calculationCreditScoring(PersonalData personalData) throws InterruptedException {
        CreditScore creditScore = new CreditScore();
        creditScore.setId(personalData.getId());
        KieSession kieSession = kieContainer.newKieSession();
        kieSession.insert(personalData);
        kieSession.setGlobal("creditScore", creditScore);
        kieSession.fireAllRules();
        kieSession.dispose();
        Mono<CreditScore> creditScoreMono = Mono.just(creditScore);
        sendMessage(creditScoreMono);
        return creditScoreMono;
    }

    private void sendMessage(Mono<CreditScore> creditScoreMono) throws InterruptedException {
        SampleProducer producer = new SampleProducer("localhost:9092");
        CountDownLatch latch = new CountDownLatch(1);
        producer.sendMessages("credit-score-topic",creditScoreMono, latch);
        latch.await(10, TimeUnit.SECONDS);
    }

    private <T> T fromBinary(String object, Class<T> resultType) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.findAndRegisterModules();
            return objectMapper.readValue(object, resultType);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
