package com.rs.ms.in.models;

public class InstagramData {

	private String type;
	private InstagramUserModel instagramUser;
	private InWebhookObject inWebhookObject;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public InstagramUserModel getInstagramUser() {
		return instagramUser;
	}
	public void setInstagramUser(InstagramUserModel instagramUser) {
		this.instagramUser = instagramUser;
	}
	public InWebhookObject getInWebhookObject() {
		return inWebhookObject;
	}
	public void setInWebhookObject(InWebhookObject inWebhookObject) {
		this.inWebhookObject = inWebhookObject;
	}
}
