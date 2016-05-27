package com.rs.ms.twitter.models;

import twitter4j.User;

import twitter4j.Status;
import twitter4j.UserList;

public class TwUserDataStream {
	
	private String object;
	private String action;
	//source=owner
	private User owner;
	//target=action taken upon
	private User target;
	private UserList userList;
	private Status status;
	
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	public User getTarget() {
		return target;
	}
	public void setTarget(User target) {
		this.target = target;
	}
	public UserList getUserList() {
		return userList;
	}
	public void setUserList(UserList userList) {
		this.userList = userList;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public String getObject() {
		return object;
	}
	public void setObject(String object) {
		this.object = object;
	}
}
