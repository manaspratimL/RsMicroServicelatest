package com.rs.ms.common.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.rs.ms.common.models.MessageType.MediaType;

import java.io.StringReader;

public class DefaultMessage implements Message {

	@SerializedName("type")
	private MediaType type;
	private String message;

	@Override
	public MediaType type() {
		return this.type;
	}

	@Override
	public String message() {
		return this.message;
	}

	public static Message parse(String message) {
		JsonReader reader = new JsonReader(new StringReader(message));
    	reader.setLenient(true);
		return new Gson().fromJson(reader, DefaultMessage.class);
	}
}
