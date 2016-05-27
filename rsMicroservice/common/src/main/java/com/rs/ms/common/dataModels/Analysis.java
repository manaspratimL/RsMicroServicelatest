package com.rs.ms.common.dataModels;

import java.util.List;

public class Analysis {

	private String fow;
	//create sentiment object
	private Sentiment sentiment;
	//entity object {name, link}
	private List<NamedEntity> ner;
	//TODO: Discuss later
	private List<String> triggers;
	//Convert to enum
	private String type;
	
	
	public String getFow() {
		return fow;
	}
	public void setFow(String fow) {
		this.fow = fow;
	}
	
	public List<String> getTriggers() {
		return triggers;
	}
	public void setTriggers(List<String> triggers) {
		this.triggers = triggers;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Sentiment getSentiment() {
		return sentiment;
	}
	public void setSentiment(Sentiment sentiment) {
		this.sentiment = sentiment;
	}
	public List<NamedEntity> getNer() {
		return ner;
	}
	public void setNer(List<NamedEntity> ner) {
		this.ner = ner;
	}
	
}
