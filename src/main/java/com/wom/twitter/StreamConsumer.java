package com.wom.twitter;

import com.google.common.collect.Lists;
import com.twitter.Extractor;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.Location;
import com.twitter.hbc.core.endpoint.Location.Coordinate;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.wom.service.KafkaSenderService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import twitter4j.JSONObject;

@Component
public class StreamConsumer {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(StreamConsumer.class);

	private BlockingQueue<String> queue = new LinkedBlockingQueue<String>(1000);

	@Autowired
	KafkaSenderService kafkaSenderService;

	@Autowired
	TwitterTemplateCreator templateCreator;

	@Autowired
	SimpMessagingTemplate template;

	@Autowired
	Extractor extractor;

	private Client client;

	@PostConstruct
	public void initialize() throws Exception {
		StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();
		
		// add some track terms
		endpoint.trackTerms(Lists.newArrayList("new car", "used car",
				"#newcar", "#usedcar"));
		Coordinate northeast = new Coordinate(-96.744232, 32.964027);
		Coordinate southwest = new Coordinate(-96.812897, 32.88939);
		Location dallasLocation = new Location(southwest, northeast);
		List<Location> locations = new ArrayList<Location>();
		locations.add(dallasLocation);
		// endpoint.locations(locations);
		endpoint.languages(Lists.newArrayList("en"));
		// endpoint.followings(Arrays.asList(913183003760787469L, 30699754L));
		// endpoint.filterLevel(FilterLevel.Medium);
		// Authentication auth = new OAuth1(consumerKey, consumerSecret, token,
		// secret);
		// Authentication auth = new BasicAuth(username, password);

		// Create a new BasicClient. By default gzip is enabled.
		client = new ClientBuilder().hosts(Constants.STREAM_HOST)
				.endpoint(endpoint).authentication(templateCreator.authenticationFor("saif"))
				.processor(new StringDelimitedProcessor(queue)).build();

		// Establish a connection
		client.connect();
		

		LOGGER.info("Connected to twitter API");
		
		
	}

	@Async
	public void consumeTweets() {
		LOGGER.info("Consuming tweets");
		// Do whatever needs to be done with messages
		for (; true;) {
			try {
				String msg = queue.take();
				JSONObject json = new JSONObject(msg);
				String text = (String) json.get("text");
				JSONObject user = (JSONObject) json.get("user");

				if (text.matches(".*car.*")) {

					LOGGER.info("Match found for name='{}' , location='{}' ,"
							+ " message ='{}'", user.get("name"),
							user.get("location"), text);

					if (text.matches(".*new.*|.*New.*")) {
						kafkaSenderService.produce("new", "cars");
						template.convertAndSend("/topic/tweets/new", msg);
					} else if (text.matches(".*used.*")) {
						kafkaSenderService.produce("used", "cars");
						template.convertAndSend("/topic/tweets/used", msg);
					}
				} else {
					LOGGER.info("No match found for payload='{}'", text);
				}
			} catch (Exception e) {
				LOGGER.error("Failure consuming message", e);
			}
		}
	}

	@PreDestroy
	public void terminate() throws Exception {
		if (client != null) {
			client.stop();
			LOGGER.info("Disconnected from twitter API");
		}
	}
}