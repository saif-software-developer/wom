package com.wom.twitter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class RunStreamConsumer implements
		ApplicationListener<ContextRefreshedEvent> {
	@Autowired
	StreamConsumer consumer;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		consumer.consumeTweets();
	}
}
