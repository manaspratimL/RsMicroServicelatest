package com.rs.ms.fb.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FbWebhookEntry {

	private String uid;
	private String id;
	private Date time;
	private List<String> changed_fields = new ArrayList<String>();
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
	

	public List<Change> getChanges() {
		return changes;
	}

	public void setChanges(List<Change> changes) {
		this.changes = changes;
	}

	public List<String> getChanged_fields() {
		return changed_fields;
	}

	public void setChanged_fields(List<String> changed_fields) {
		this.changed_fields = changed_fields;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}


}
