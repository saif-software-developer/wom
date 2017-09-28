package com.wom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wom.kafka.consumer.KafkaReceiver;

@RestController
@RequestMapping (value = "/wom-consumer/")
public class KafkaReceiverController {

	  @Autowired
	  private KafkaReceiver kafkaReceiver;
	  
		@GetMapping(value = "/consume")
		public String produce(@RequestParam("tweet") String tweet) {

			return "Tweet was consumed successfully !!!";
		}

}
