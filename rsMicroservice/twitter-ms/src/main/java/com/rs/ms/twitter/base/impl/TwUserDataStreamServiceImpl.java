package com.rs.ms.twitter.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rs.ms.common.kafka.KafkaProducerService;
import com.rs.ms.twitter.base.TwUserDataStreamService;
import com.rs.ms.twitter.models.TwUserDataStream;
import com.rs.ms.twitter.models.TwitterData;

import twitter4j.Status;
import twitter4j.User;
import twitter4j.UserList;

@Service
public class TwUserDataStreamServiceImpl implements TwUserDataStreamService{
	
	@Autowired
	private KafkaProducerService kafkaProducer;

	
	
	/**
	 */
	public TwitterData createUserDataStreamObj(User owner,User target,Status status,String action,UserList userList){
		
		TwUserDataStream userData= new TwUserDataStream();
		userData.setAction(action);
		userData.setOwner(owner);
		userData.setTarget(target);
		userData.setStatus(status);
		userData.setUserList(userList);
		userData.setObject("STREAM");
		
		TwitterData data= new TwitterData();
		data.setType("STREAM");
		data.setStreamData(userData);
		return data;
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	@Async
	public void pulishToKafka(TwitterData userStreamData) throws Exception{
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		String json = gson.toJson(userStreamData);
		System.out.println(json);

		// produce to kafka
		kafkaProducer.publishUpdates(json, "rsmstw");
	}
}
