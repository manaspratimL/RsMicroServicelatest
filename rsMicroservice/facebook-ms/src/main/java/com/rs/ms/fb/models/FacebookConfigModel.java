package com.rs.ms.fb.models;

public class FacebookConfigModel {

	private String _id;
	private String email;
	private String name;
	private String token;
	private String facebookId;
	private int _v;
	private boolean isOld=true;
	private String machineId;
	
	/**
	 * @return the _id
	 */
	public String get_id() {
		return _id;
	}
	/**
	 * @param _id the _id to set
	 */
	public void set_id(String _id) {
		this._id = _id;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
	/**
	 * @return the facebookId
	 */
	public String getFacebookId() {
		return facebookId;
	}
	/**
	 * @param facebookId the facebookId to set
	 */
	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}
	/**
	 * @return the _v
	 */
	public int get_v() {
		return _v;
	}
	/**
	 * @param _v the _v to set
	 */
	public void set_v(int _v) {
		this._v = _v;
	}
	public boolean isOld() {
		return isOld;
	}
	public void setOld(boolean isOld) {
		this.isOld = isOld;
	}
	public String getMachineId() {
		return machineId;
	}
	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}
}
