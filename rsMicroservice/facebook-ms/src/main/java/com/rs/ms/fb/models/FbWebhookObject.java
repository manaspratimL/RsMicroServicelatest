package com.rs.ms.fb.models;

import java.util.ArrayList;
import java.util.List;

public class FbWebhookObject {
	private String object;
	
	private List<FbWebhookEntry> entry = new ArrayList<FbWebhookEntry>();

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public List<FbWebhookEntry> getEntry() {
		return entry;
	}

	public void setEntry(List<FbWebhookEntry> entry) {
		this.entry = entry;
	}

	
}
