package com.rs.ms.twitter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.rs.ms.common.dataModels.RsStream;
import com.rs.ms.common.dataModels.RsStreams;
import com.rs.ms.common.kafka.KafkaProducerService;
import com.rs.ms.twitter.base.TwitterStatusListener;
import com.rs.ms.twitter.base.impl.TwitterStatusListenerImpl;
import com.rs.ms.twitter.models.TwitterData;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by babluj on 5/9/16.
 */
@Service
public class TwitterClient implements User {

	private final static Twitter TWITTER = TwitterFactory.getSingleton();

	@Autowired
	private TwitterStatusListener listener;

	@Value("${paging.pages}")
	private Integer batches;

	@Value("${paging.count.per.page}")
	private Integer batchSize;

	private final KafkaProducerService kafkaProducerService;

	@Autowired
	public TwitterClient(KafkaProducerService kafkaProducerService) {
		this.kafkaProducerService = kafkaProducerService;
	}

	@Override
	public TwitterData getTimeLine(TwitterConfig config) throws TwitterException {

		Paging paging = new Paging(1,20);
		TWITTER.setOAuthAccessToken(new AccessToken(config.getToken(), config.getTokenSecret()));

		addStreamListener(config);
		// fetch user home timeline and create an RS timeline
		TimeLine timeline=new TimeLine(TWITTER.getHomeTimeline(paging));
		
		RsStreams rsStreams=createRsStreamsObj(timeline,config);
		
		
		TwitterData data= new TwitterData();
		data.setType("FEED");
		data.setTimeline(timeline);
		
		return data;
	}


	@Async
	private void addStreamListener(TwitterConfig config) throws TwitterException {

		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setJSONStoreEnabled(true).setUserStreamRepliesAllEnabled(true).setOAuthAccessToken(config.getToken()).setOAuthAccessTokenSecret(config.getTokenSecret());


		TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
		twitterStream.addListener(new TwitterStatusListenerImpl());
		twitterStream.user();
	
		

	}

	public static void main(String... args) throws Exception {
		/*new KafkaProducerService().publishUpdates(new Gson().toJson(new TwitterClient(null)
				.getTimeLine(new TwitterConfig("2676551822-pzyFof4wm5uTOow1hABQYnd8BDQuujFu4PN6FnO",
						"Rhpm0xgxUTAQ59uZT4oi1vCWENlueTpmupuqWzupO9UyJ"))),
				"rsmstw");*/
		
		TwitterClient client= new TwitterClient(null);
		TwitterConfig config= new TwitterConfig("2676551822-pzyFof4wm5uTOow1hABQYnd8BDQuujFu4PN6FnO","Rhpm0xgxUTAQ59uZT4oi1vCWENlueTpmupuqWzupO9UyJ");
		client.addStreamListener(config);
	}
	
	
	private RsStreams createRsStreamsObj(TimeLine timeline, TwitterConfig config) {
		
		for(Status status : timeline.getStatuses()){
			
			RsStream stream=createRsStreamObj(status,config);
			
		}
		
		return null;
	}

	private RsStream createRsStreamObj(Status status, TwitterConfig config) {
		
		RsStream stream = new RsStream();
		stream.getSourceInfo().setName("TWITTER");
		stream.getSourceInfo().setInteractionType("PUBLIC");
		
		stream.setContent(status.getText());
		stream.setLikesCount(status.getFavoriteCount());
		stream.setReTweetedCount(status.getRetweetCount());
		
		stream.getLocation().setLongitude(String.valueOf(status.getGeoLocation().getLongitude()));
		stream.getLocation().setLatitude(String.valueOf(status.getGeoLocation().getLatitude()));
		
		
		return null;
	}
}
