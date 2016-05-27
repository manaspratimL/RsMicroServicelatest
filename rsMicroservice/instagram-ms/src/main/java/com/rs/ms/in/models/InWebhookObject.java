package com.rs.ms.in.models;

import java.util.ArrayList;
import java.util.List;

public class InWebhookObject {
	private String object;
	
	private List<InWebhookEntry> entryList = new ArrayList<InWebhookEntry>();

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public List<InWebhookEntry> getEntryList() {
		
		if(entryList==null){
			 entryList = new ArrayList<InWebhookEntry>();
		}
		
		return entryList;
	}

	public void setEntryList(List<InWebhookEntry> entryList) {
		this.entryList = entryList;
	}
}
