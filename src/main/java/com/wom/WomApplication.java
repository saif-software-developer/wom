package com.wom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
@EnableKafka
@ComponentScan(basePackages = {"com.wom"})
public class WomApplication {
	  
	public static void main(String[] args) {
		SpringApplication.run(WomApplication.class, args);
	}
}
