package com.wom.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.TweetData;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wom.model.ReplyTo;
import com.wom.twitter.TwitterTemplateCreator;

@RestController
@RequestMapping("/api")
public class ApiController {
	  private static final Logger LOGGER = LoggerFactory.getLogger(ApiController.class);

	@Autowired
	TwitterTemplateCreator templateCreator;

	@PostMapping("/reply-to")
	public void replyTo(@RequestBody ReplyTo replyTo) {
		LOGGER.info("Replying to tweet: {} as: {}", 
				replyTo.getStatusId(), replyTo.getFrom());
		TwitterTemplate template = templateCreator.templateFor(replyTo.getFrom());
		template.timelineOperations().updateStatus(
				new TweetData(replyTo.getMessage())
				.inReplyToStatus(replyTo.getStatusId()));
	}
}
