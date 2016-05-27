package com.rs.ms.fb.models;

public class Configuration {

	private String provider;
	
	private FacebookConfigModel fbConfiguration;

	
	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public FacebookConfigModel getFbConfiguration() {
		return fbConfiguration;
	}

	public void setFbConfiguration(FacebookConfigModel fbConfiguration) {
		this.fbConfiguration = fbConfiguration;
	}
}
