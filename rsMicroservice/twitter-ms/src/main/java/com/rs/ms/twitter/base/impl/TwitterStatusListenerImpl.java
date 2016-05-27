package com.rs.ms.twitter.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rs.ms.twitter.base.TwitterStatusListener;
import com.rs.ms.twitter.models.TwitterData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rs.ms.twitter.base.TwUserDataStreamService;
import twitter4j.DirectMessage;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.User;
import twitter4j.UserList;

@Service
public class TwitterStatusListenerImpl implements TwitterStatusListener{
	
	@Autowired
	private TwUserDataStreamService userStreamService;

	@Override
	public void onDeletionNotice(long directMessageId, long userId) {
		System.out.println(userId);
		
	}

	@Override
	public void onFriendList(long[] friendIds) {
		System.out.println(friendIds);
		
	}

	@Override
	public void onFavorite(User source, User target, Status favoritedStatus) {
		System.out.println(source);
		TwitterData userdata=userStreamService.createUserDataStreamObj(source, target, null,"onFavorite", null);
		try {
			userStreamService.pulishToKafka(userdata);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}

	@Override
	public void onUnfavorite(User source, User target, Status unfavoritedStatus) {
		System.out.println(source);
		TwitterData userdata=userStreamService.createUserDataStreamObj(source, target, null,"onUnfavorite", null);
		try {
			userStreamService.pulishToKafka(userdata);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}

	@Override
	public void onFollow(User source, User followedUser) {
		System.out.println(followedUser);
		
	}

	@Override
	public void onUnfollow(User source, User unfollowedUser) {
		System.out.println(unfollowedUser);
		
	}

	@Override
	public void onDirectMessage(DirectMessage directMessage) {
		System.out.println(directMessage);
		
	}

	@Override
	public void onUserListMemberAddition(User addedMember, User listOwner, UserList list) {
		System.out.println(addedMember);
		
	}

	@Override
	public void onUserListMemberDeletion(User deletedMember, User listOwner, UserList list) {
		System.out.println(deletedMember);
		
	}

	@Override
	public void onUserListSubscription(User subscriber, User listOwner, UserList list) {
		System.out.println(subscriber);
		
	}

	@Override
	public void onUserListUnsubscription(User subscriber, User listOwner, UserList list) {
		System.out.println(subscriber);
		
	}

	@Override
	public void onUserListCreation(User listOwner, UserList list) {
		System.out.println(listOwner);
		
	}

	@Override
	public void onUserListUpdate(User listOwner, UserList list) {
		System.out.println(listOwner);
		
	}

	@Override
	public void onUserListDeletion(User listOwner, UserList list) {
		System.out.println(listOwner);
		
	}

	@Override
	public void onUserProfileUpdate(User updatedUser) {
		System.out.println(updatedUser);
		
	}

	@Override
	public void onUserSuspension(long suspendedUser) {
		System.out.println(suspendedUser);
		
	}

	@Override
	public void onUserDeletion(long deletedUser) {
		System.out.println(deletedUser);
		
	}

	@Override
	public void onBlock(User source, User blockedUser) {
		System.out.println(blockedUser);
		
	}

	@Override
	public void onUnblock(User source, User unblockedUser) {
		System.out.println(unblockedUser);
		
	}

	@Override
	public void onRetweetedRetweet(User source, User target, Status retweetedStatus) {
		System.out.println(retweetedStatus);
		
	}

	@Override
	public void onFavoritedRetweet(User source, User target, Status favoritedRetweeet) {
		System.out.println(favoritedRetweeet);
		TwitterData userdata=userStreamService.createUserDataStreamObj(source, target, favoritedRetweeet,"onFavoritedRetweet", null);
		try {
			userStreamService.pulishToKafka(userdata);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}

	@Override
	public void onQuotedTweet(User source, User target, Status quotingTweet) {
		System.out.println(quotingTweet);
		TwitterData userdata=userStreamService.createUserDataStreamObj(source, target, quotingTweet,"onQuotedTweet", null);
		try {
			userStreamService.pulishToKafka(userdata);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}

	@Override
	public void onException(Exception ex) {
		System.out.println(ex);
		
	}

	@Override
	public void onStatus(Status status) {
		//System.out.println(status);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		String json = gson.toJson(status);
		System.out.println(json);
		/*TwitterData userdata=userStreamService.createUserDataStreamObj(null, null, status,"onStatus", null);
		try {
			userStreamService.pulishToKafka(userdata);
		} catch (Exception e) {
			
			e.printStackTrace();
		}*/
	}

	@Override
	public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
		System.out.println(statusDeletionNotice);
		
	}

	@Override
	public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
		System.out.println(numberOfLimitedStatuses);
		
	}

	@Override
	public void onScrubGeo(long userId, long upToStatusId) {
		System.out.println(upToStatusId);
		
	}

	@Override
	public void onStallWarning(StallWarning warning) {
		System.out.println(warning);
		
	}

}
