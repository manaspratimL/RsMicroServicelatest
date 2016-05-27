package com.rs.ms.in.models;

import java.util.List;

import org.jinstagram.entity.users.feed.MediaFeedData;


public class InstagramUserModel {

	private String userId;
	private String leadId;
	
	private List<MediaFeedData> post;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLeadId() {
		return leadId;
	}

	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}

	public List<MediaFeedData> getPost() {
		return post;
	}

	public void setPost(List<MediaFeedData> post) {
		this.post = post;
	}
}
