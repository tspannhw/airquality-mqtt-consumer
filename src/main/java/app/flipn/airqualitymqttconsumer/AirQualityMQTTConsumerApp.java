package app.flipn.airqualitymqttconsumer;

import app.flipn.airqualitymqttconsumer.model.GenericMessage;
import app.flipn.airqualitymqttconsumer.service.MQTTService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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

	@Scheduled(initialDelay = 0, fixedRate = 30000)
	public void repeatRun()
	{
		mqttService.consume();
	}

	@Override
	public void run(String... args) {
		mqttService.consume();
	}
}