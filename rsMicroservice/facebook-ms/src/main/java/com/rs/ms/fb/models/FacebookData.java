package com.rs.ms.fb.models;

import com.restfb.types.webhook.WebhookObject;

public class FacebookData {

	private String type;
	private FacebookUserModel facebookUser;
	private FacebookPageModel facebookPage;
	private WebhookObject webhookObject;
	
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the facebookUser
	 */
	public FacebookUserModel getFacebookUser() {
		return facebookUser;
	}
	/**
	 * @param facebookUser the facebookUser to set
	 */
	public void setFacebookUser(FacebookUserModel facebookUser) {
		this.facebookUser = facebookUser;
	}
	/**
	 * @return the facebookPage
	 */
	public FacebookPageModel getFacebookPage() {
		return facebookPage;
	}
	/**
	 * @param facebookPage the facebookPage to set
	 */
	public void setFacebookPage(FacebookPageModel facebookPage) {
		this.facebookPage = facebookPage;
	}
	/**
	 * @return the webhookObject
	 */
	public WebhookObject getWebhookObject() {
		return webhookObject;
	}
	/**
	 * @param webhookObject the webhookObject to set
	 */
	public void setWebhookObject(WebhookObject webhookObject) {
		this.webhookObject = webhookObject;
	}
	
}
