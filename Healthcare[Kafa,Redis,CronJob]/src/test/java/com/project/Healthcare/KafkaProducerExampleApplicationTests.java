package com.project.Healthcare;

import com.project.Healthcare.model.Consultation;
import com.project.Healthcare.service.KafkaEventsProducer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class KafkaProducerExampleApplicationTests {

	@Container
	static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));

	@DynamicPropertySource
	public static void initKafkaProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
	}

	@Autowired
	private KafkaEventsProducer publisher;

	@Test
	public void testSendEventsToTopic() {
		LocalDateTime dateTime = LocalDateTime.of(2024, 7, 15, 15, 30, 0);

//		publisher.sendPatientToTopic(new Consultation("263",
//				"12345", "nothing", dateTime));
//		await().pollInterval(Duration.ofSeconds(3))
//				.atMost(10, TimeUnit.SECONDS).untilAsserted(() -> {
//					// assert statement
//				});
//	}
	}
}
