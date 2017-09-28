package com.wom.kafka.consumer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public class KafkaReceiver {

  private static final Logger LOGGER = LoggerFactory.getLogger(KafkaReceiver.class);
  
  @Autowired
  private List<String> usedCarsBean;
  
  @Autowired
  private List<String> newCarsBean;
  

  @KafkaListener(topicPattern = "${kafka.topic.newCars}")
  public void receiveNewCars(String payload) {
    LOGGER.info("received payload='{}'", payload);
//    template.convertAndSend(arg0);
    newCarsBean.add(payload);
  }
  
  @KafkaListener(topicPattern = "${kafka.topic.usedCars}")
  public void receiveUsedCars(String payload) {
    LOGGER.info("received payload='{}'", payload);
    usedCarsBean.add(payload);
  }
  
}
