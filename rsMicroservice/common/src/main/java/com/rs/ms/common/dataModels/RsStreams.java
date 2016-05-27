package com.rs.ms.common.dataModels;

import java.util.ArrayList;
import java.util.List;

public class RsStreams {
	
	private String socialMediaUUID;
	
	private String provider;

	private List<RsStream> rsStream=null;

	public List<RsStream> getRsStream() {
		if(rsStream==null){
			rsStream= new ArrayList<>();
		}
		return rsStream;
	}

	public void setRsStream(List<RsStream> rsStream) {
		this.rsStream = rsStream;
	}

	public String getSocialMediaUUID() {
		return socialMediaUUID;
	}

	public void setSocialMediaUUID(String socialMediaUUID) {
		this.socialMediaUUID = socialMediaUUID;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	
}
