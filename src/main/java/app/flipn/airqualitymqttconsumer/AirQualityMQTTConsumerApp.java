package app.flipn.airqualitymqttconsumer;

import app.flipn.airqualitymqttconsumer.*;
import app.flipn.airqualitymqttconsumer.model.GenericMessage;
import app.flipn.airqualitymqttconsumer.model.Observation;
import app.flipn.airqualitymqttconsumer.service.MQTTService;
import app.flipn.airqualitymqttconsumer.service.PulsarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Calendar;
import java.util.List;

@EnableScheduling
@SpringBootApplication
public class AirQualityMQTTConsumerApp  implements CommandLineRunner {
	private static final Logger log = LoggerFactory.getLogger(AirQualityMQTTConsumerApp.class);

	/**
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(AirQualityMQTTConsumerApp.class, args);
	}

	@Autowired
	private MQTTService mqttService;

	@Autowired
	private PulsarService pulsarService;

	/**
	 *
	 */
	private void display() {
		// MQTT
		List<GenericMessage> mqttList = mqttService.consume();

		if ( mqttList != null & mqttList.size() > 0){
			// MQTT
			for (GenericMessage message : mqttList) {
				if ( message != null) {
					System.out.println("MQTT:" + message);
				}
			}
		}

		// Pulsar
		List<Observation> observationList = pulsarService.consume();

		if ( observationList != null && observationList.size() > 0) {
			for (Observation observation : observationList) {
				if ( observation != null) {
					System.out.println("Pulsar:" + observation);
				}
			}
		}

	}

	@Scheduled(initialDelay = 0, fixedRate = 30000)
	public void repeatRun()
	{
		log.debug("Repeat Run. Current time is :: " + Calendar.getInstance().getTime());
		display();
	}

	@Override
	public void run(String... args) {
		display();
	}
}
