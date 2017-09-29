package com.wom.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(path = "/wom-ui")
public class WOMController {

	private static final Logger LOGGER = LoggerFactory.getLogger(WOMController.class);

	@GetMapping(value = "/newCars")
	public String newCars() {
	    LOGGER.info("A new request was recived to process check new cars api");
		return "twitter-consumer-admin";
	}

	@GetMapping(value = "/usedCars")
	public String usedCars() {
	    LOGGER.info("A new request was recived to process check used cars api");
		return "twitter-consumer-admin";
	}
	
	@GetMapping("/latest-tweets")
	public String latestTweets() {
		return "latest-tweets";
	}
	
	@GetMapping("/index")
	public String index() {
		return "index";
	}
}
