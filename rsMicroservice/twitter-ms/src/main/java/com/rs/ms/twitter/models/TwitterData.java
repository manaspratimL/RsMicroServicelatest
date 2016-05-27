package com.rs.ms.twitter.models;

import com.rs.ms.twitter.TimeLine;

public class TwitterData {

	private String type;
	private TimeLine timeline;
	private TwUserDataStream streamData;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public TwUserDataStream getStreamData() {
		return streamData;
	}
	public void setStreamData(TwUserDataStream streamData) {
		this.streamData = streamData;
	}
	public TimeLine getTimeline() {
		return timeline;
	}
	public void setTimeline(TimeLine timeline) {
		this.timeline = timeline;
	}
}
