package com.rs.ms.in.models;

public class InstagramConfigModel {
	
	private String _v;
	private String _id;
	private String provider;
	private String displayName;
	private String token;
	private String instagramId;
	private String secret;
	
	public String get_v() {
		return _v;
	}
	public void set_v(String _v) {
		this._v = _v;
	}
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getInstagramId() {
		return instagramId;
	}
	public void setInstagramId(String instagramId) {
		this.instagramId = instagramId;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
}
