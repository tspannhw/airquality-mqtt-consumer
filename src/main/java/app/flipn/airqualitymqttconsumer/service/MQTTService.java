package app.flipn.airqualitymqttconsumer.service;

import app.flipn.airqualitymqttconsumer.config.PulsarConfig;
import app.flipn.airqualitymqttconsumer.model.GenericMessage;
import app.flipn.airqualitymqttconsumer.model.Observation;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * service for mqtt messages
 */
@Service
public class MQTTService {
    private static final Logger log = LoggerFactory.getLogger(MQTTService.class);

    @Value("${mqtt.topic:airqualitymqtt}")
    String topicName;

    @Value("${mqtt.wait.time:5000}")
    long waitMillis;

    @Autowired
    private IMqttClient mqttClient;

    /**
     *
     * @return List<GenericMessage> messages
     */
    public List<GenericMessage> consume() {
        List<GenericMessage> messages = new ArrayList<GenericMessage>();
        try {
            CountDownLatch countDownLatch = new CountDownLatch(10);
            mqttClient.subscribeWithResponse(topicName, (s, mqttMessage) -> {
                GenericMessage msg = new GenericMessage();
                msg.setId(mqttMessage.getId());
                msg.setPayload(new String(mqttMessage.getPayload()));
                msg.setQos(mqttMessage.getQos());
                messages.add(msg);
                countDownLatch.countDown();
            });

            try {
                countDownLatch.await(waitMillis, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                log.error("Interrupted", e);
            }
        } catch (MqttException e) {
            log.error("MQTT Error", e);
        }

        return messages;
    }
}
