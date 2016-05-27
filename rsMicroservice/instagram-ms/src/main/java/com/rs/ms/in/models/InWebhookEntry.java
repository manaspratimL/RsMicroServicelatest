package com.rs.ms.in.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InWebhookEntry {

	private String uid;
	private String id;
	private String time;
	private List<String> changedFields = new ArrayList<String>();
	private List<Change> changes = new ArrayList<Change>();

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public List<String> getChangedFields() {
		if(changedFields==null){
			changedFields= new ArrayList<>();
		}
		return changedFields;
	}

	public void setChangedFields(List<String> changedFields) {
		this.changedFields = changedFields;
	}

	public List<Change> getChanges() {
		if(changes==null){
			changes=new ArrayList<Change>();
		}
		return changes;
	}

	public void setChanges(List<Change> changes) {
		this.changes = changes;
	}

}
