package app.flipn.airqualitymqttconsumer.service;

import app.flipn.airqualitymqttconsumer.model.Observation;
import org.apache.pulsar.client.api.MessageId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 *
 */
@Service
public class AMQPService {
    private static final Logger log = LoggerFactory.getLogger(AMQPService.class);

    @Value("${amqp.topic:amqp-airquality}")
    String topicName;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Component
    public class RabbitMQConsumer {
//        @RabbitListener(queues = "${amqp.topic:amqp-airquality}")
//        public void receiveMessage(Observation obs) {
//            System.out.println("Recieved Message From RabbitMQ: " + obs);
//        }
//        }
        @RabbitListener(queues = "${amqp.topic:amqp-airquality}")
        public void receiveMessage(String obs) {
            System.out.println("RabbitMQ: " + obs);
        }
    }
}
