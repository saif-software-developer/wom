package com.wom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wom.service.KafkaSenderService;

@RestController
@RequestMapping(value = "/wom-sender/")
public class KafkaSenderController {

	@Autowired
	KafkaSenderService kafkaSenderService;

	@GetMapping(value = "/tweet")
	public String produce(@RequestParam("topic") String topic, @RequestParam("tweet") String tweet) {
		System.out.println("produce .... ");
		kafkaSenderService.produce(topic,tweet);
		System.out.println("sent !!! ");
		return "Tweet was sent successfully !!!";
	}

}
