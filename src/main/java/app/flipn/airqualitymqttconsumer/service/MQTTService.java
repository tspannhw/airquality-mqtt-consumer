package app.flipn.airqualitymqttconsumer.service;

import app.flipn.airqualitymqttconsumer.model.GenericMessage;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
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


    public void consume() {
        try {
            CountDownLatch countDownLatch = new CountDownLatch(10);
            log.info("topic:" + topicName);
            mqttClient.subscribeWithResponse(topicName, (s, mqttMessage) -> {
                log.info("MQTT ID:"+mqttMessage.getId());
                log.info("MQTT:" + new String(mqttMessage.getPayload()));
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
    }
}