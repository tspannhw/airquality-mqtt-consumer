package app.flipn.airqualitymqttconsumer.service;

import app.flipn.airqualitymqttconsumer.model.GenericMessage;
import app.flipn.airqualitymqttconsumer.model.Observation;
import org.apache.pulsar.client.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 *
 */
@Service
public class PulsarService {
    private static final Logger log = LoggerFactory.getLogger(PulsarService.class);
    private static final int MAX_COUNT = 5;

    @Autowired
    PulsarClient pulsarClient;

    @Autowired
    Consumer<Observation> consumer;

    /**
     * consume to pulsar
     *
     * @return List Observation
     */
    public List<Observation> consume() {
        List<Observation> messages = new ArrayList<Observation>();

        int count = 0;

        do {
            // Wait until a message is available
            Message<Observation> msg = null;
            try {
                msg = consumer.receive();
            } catch (PulsarClientException e) {
                log.error("Consume Failure", e);
            }
//            System.out.println("Pulsar  v:" + msg.getValue());
//            System.out.println("Received k:" + msg.getKey());
//            System.out.println("Received p,t:" + msg.getProducerName()
//                    + "," + msg.getEventTime() );
//            System.out.println("Received ID:" + msg.getMessageId() );

            messages.add(msg.getValue());

            // Acknowledge processing of the message so that it can be deleted
            try {
                consumer.acknowledge(msg);
               // System.out.println("Total Messages Received: " + consumer.getStats().getTotalMsgsReceived());
            } catch (PulsarClientException e) {
                log.error("Consume Ack Failure", e);
            }
            count++;
        } while (count < MAX_COUNT );

        return messages;
    }
}
