package com.wom.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class TwitterDao {

	
	private List<String> usedCars=new ArrayList<String>();
	private List<String> newCars=new ArrayList<String>();
	
	@Bean
	public List<String> usedCarsBean(){
		return usedCars;
	}
	
	@Bean
	public List<String> newCarsBean(){
		return newCars;
	}
	
}
