package com.rs.ms.twitter.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.rs.ms.common.kafka.KafkaProducerService;
import com.rs.ms.common.models.MessageHandler;
import com.rs.ms.twitter.TwitterClient;
import com.rs.ms.twitter.TwitterConfig;

@Service
public class TwitterMessageHandler implements MessageHandler {

    private Gson gson;
    private final TwitterClient twitterClient;
    private final KafkaProducerService kafkaProducerService;

    @Autowired
    public TwitterMessageHandler(TwitterClient twitterClient, KafkaProducerService kafkaProducerService) {
        this.twitterClient = twitterClient;
        this.kafkaProducerService = kafkaProducerService;
        this.gson = new Gson();
    }

    @Override
    public void handleMessage(String message) {

        TwitterConfig twitterConfig = gson.fromJson(message, TwitterConfig.class);
        try {
            kafkaProducerService.publishUpdates(gson.toJson(twitterClient.getTimeLine(twitterConfig)), "rsmstw");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
