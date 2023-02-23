package io.sohan.DemoRabbitMQ;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DemoRabbitMqApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoRabbitMqApplication.class, args);
	}
}
