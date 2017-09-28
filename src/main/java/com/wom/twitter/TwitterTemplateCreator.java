package com.wom.twitter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Component;

import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

@Component
public class TwitterTemplateCreator {
	
	@Autowired
	private Environment environment;
	
	public TwitterTemplate templateFor(String username) {
		String consumerKey = environment.getProperty(username + ".consumerKey");
		String consumerSecret = environment.getProperty(username + ".consumerSecret");
		String accessToken = environment.getProperty(username + ".accessToken");
		String accessTokenSecret = environment.getProperty(username + ".accessTokenSecret");
		return new TwitterTemplate(consumerKey, consumerSecret, accessToken, accessTokenSecret);
	}
	
	public Authentication authenticationFor(String username) {
		String consumerKey = environment.getProperty(username + ".consumerKey");
		String consumerSecret = environment.getProperty(username + ".consumerSecret");
		String accessToken = environment.getProperty(username + ".accessToken");
		String accessTokenSecret = environment.getProperty(username + ".accessTokenSecret");
		return new OAuth1(consumerKey, consumerSecret, accessToken, accessTokenSecret);
	}
}
