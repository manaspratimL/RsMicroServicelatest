package com.rs.ms.kafka;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.rs.ms.twitter.kafka.TwitterMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rs.ms.common.models.MessageHandler;
import com.rs.ms.common.models.MessageType;
import com.rs.ms.fb.kafka.FacebookMessageHandler;
import com.rs.ms.in.kafka.InstagramMessageHandler;

@Service
public class MessageTypes implements MessageType {
	
	private Map<MediaType, MessageHandler> handlers;
	private final FacebookMessageHandler facebookMessageHandler;
	private final InstagramMessageHandler instagramMessageHandler;
    private final TwitterMessageHandler twitterMessageHandler;

	@Autowired
	public MessageTypes(FacebookMessageHandler facebookMessageHandler,InstagramMessageHandler instagramMessageHandler, TwitterMessageHandler twitterMessageHandler) {
		this.facebookMessageHandler = facebookMessageHandler;
		this.instagramMessageHandler = instagramMessageHandler;
        this.twitterMessageHandler = twitterMessageHandler;

		handlers = new LinkedHashMap<>();
		handlers.put(MediaType.FACEBOOK, facebookMessageHandler);
		handlers.put(MediaType.INSTAGRAM, instagramMessageHandler);
        handlers.put(MediaType.TWITTER, twitterMessageHandler);
	}
	
	
	
	@Override
	public List<MediaType> mediaTypes() {
		return Arrays.asList(MediaType.values());
	}

	@Override
	public MessageHandler handler(MediaType mediaType) {
		return handlers.get(mediaType);
	}
}
