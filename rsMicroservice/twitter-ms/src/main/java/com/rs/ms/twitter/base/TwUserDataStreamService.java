package com.rs.ms.twitter.base;

import com.rs.ms.twitter.models.TwitterData;

import twitter4j.Status;
import twitter4j.User;
import twitter4j.UserList;

public interface TwUserDataStreamService {

	public TwitterData createUserDataStreamObj(User owner, User target, Status status, String action,
			UserList userList);
	
	public void pulishToKafka(TwitterData userStreamData)throws Exception;

}
