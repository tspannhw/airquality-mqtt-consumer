package app.flipn.airqualitymqttconsumer.config;

import app.flipn.airqualitymqttconsumer.model.Observation;
import org.apache.pulsar.client.api.*;
import org.apache.pulsar.client.impl.schema.JSONSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PulsarConsumerConfig {
    private static final Logger log = LoggerFactory.getLogger(PulsarConsumerConfig.class);

    @Autowired
    PulsarClient pulsarClient;

    @Value("${consumer.name:consumerName}")
    String consumerName;

    @Value("${topic.name:airquality}")
    String topicName;

    @Value("${subscription.name:airqualitysubscription}")
    String subscriptionName;

    @Bean
    public Consumer<Observation> getConsumer() {
        Consumer<Observation> pulsarConsumer = null;
        ConsumerBuilder<Observation> consumerBuilder =
                pulsarClient.newConsumer(JSONSchema.of(Observation.class))
                        .topic(topicName)
                        .subscriptionName(subscriptionName)
                        .subscriptionType(SubscriptionType.Shared)
                        .subscriptionInitialPosition(SubscriptionInitialPosition.Earliest)
                        .consumerName(consumerName);
        try {
            pulsarConsumer = consumerBuilder.subscribe();
        } catch (PulsarClientException e) {
            log.error("Consumer Builder Error", e);
        }

        return pulsarConsumer;
    }
}