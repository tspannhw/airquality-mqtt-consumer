package app.flipn.airqualitymqttconsumer;

import app.flipn.airqualitymqttconsumer.config.MQTTConfig;
import app.flipn.airqualitymqttconsumer.model.GenericMessage;
import app.flipn.airqualitymqttconsumer.service.MQTTService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.testng.AssertJUnit.assertNotNull;


@SpringBootTest(
		classes = {
				MQTTConfig.class,
				MQTTService.class
		}
)
class AirqualityMqttConsumerApplicationTests {

	@Autowired
	private MQTTService mqttService;

	@Test
	void contextLoads() {
		mqttService.consume();
	}
}