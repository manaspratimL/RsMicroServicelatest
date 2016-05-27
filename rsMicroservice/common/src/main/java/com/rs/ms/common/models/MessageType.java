package com.rs.ms.common.models;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public interface MessageType {

	public List<MediaType> mediaTypes();
	public MessageHandler handler(MediaType mediaType);

	enum MediaType {
		@SerializedName("FACEBOOK")
		FACEBOOK, 
		
		@SerializedName("TWITTER")
		TWITTER, 
		
		@SerializedName("INSTAGRAM")
		INSTAGRAM;
	}
}