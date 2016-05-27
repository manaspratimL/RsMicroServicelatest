package com.rs.ms.common.dataModels;

import java.util.List;

public class Place {
	
	private String name;
	private List<NamedEntity> ner;
	private Sentiment sentiment;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<NamedEntity> getNer() {
		return ner;
	}
	public void setNer(List<NamedEntity> ner) {
		this.ner = ner;
	}
	public Sentiment getSentiment() {
		return sentiment;
	}
	public void setSentiment(Sentiment sentiment) {
		this.sentiment = sentiment;
	}
}
