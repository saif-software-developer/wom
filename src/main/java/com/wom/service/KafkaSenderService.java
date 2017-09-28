package com.wom.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wom.kafka.producer.KafkaSender;

@Service
public class KafkaSenderService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaSenderService.class);

	@Autowired
	private KafkaSender kafkaSender;
	
	public String produce(String topic,  String tweet) {
	    LOGGER.info("sending payload='{}' to topic='{}'", tweet, topic);
		kafkaSender.send(topic,tweet);
		return "Tweet was sent successfully !!!";
	}

}
