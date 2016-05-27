package com.rs.ms.in.models;

import java.util.Date;

public class StreamData {
	
	private String changed_aspect;
	private String object;
	private String object_id;
	private Date time;
	private String subscription_id;
	private Data data;
	
	public String getChanged_aspect() {
		return changed_aspect;
	}
	public void setChanged_aspect(String changed_aspect) {
		this.changed_aspect = changed_aspect;
	}
	public String getObject() {
		return object;
	}
	public void setObject(String object) {
		this.object = object;
	}
	public String getObject_id() {
		return object_id;
	}
	public void setObject_id(String object_id) {
		this.object_id = object_id;
	}
	
	public String getSubscription_id() {
		return subscription_id;
	}
	public void setSubscription_id(String subscription_id) {
		this.subscription_id = subscription_id;
	}
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
}
