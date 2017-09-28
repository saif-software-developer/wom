package com.wom.twitter;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.twitter.Extractor;

@Component
public class TwitterExtractor {

	@Bean
	public Extractor extractor(){
		return new Extractor();
	}
}
