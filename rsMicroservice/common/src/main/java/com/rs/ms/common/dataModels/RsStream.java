package com.rs.ms.common.dataModels;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RsStream {

	private String parentSourceId;
	private String sourceId;
	private String content;
	private String story;
	private String description;
	
	// create ENUM TODO
	private String eventType;
	private String eventAction;
	
	private UserInfo source;
	private UserInfo target;
	// location object
	private Location location;
	// list of urls
	private String url;

	private List<NamedEntity> entities;

	private List<HashTag> HashTags;
	private List<Place> places;
	private List<UserInfo> userMentions;
	private MediaEntity mediaEntity;
	private long likesCount;
	private long commentCount;
	private long shareCount;
	private long reTweetedCount;
	private Date createdTime;

	private SourceInfo sourceInfo;
	private Analysis analysis;

	public String getParentSourceId() {
		return parentSourceId;
	}

	public void setParentSourceId(String parentSourceId) {
		this.parentSourceId = parentSourceId;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public SourceInfo getSourceInfo() {
		if(sourceInfo==null){
			sourceInfo= new SourceInfo();
		}
		return sourceInfo;
	}

	public void setSourceInfo(SourceInfo sourceInfo) {
		this.sourceInfo = sourceInfo;
	}

	public Analysis getAnalysis() {
		return analysis;
	}

	public void setAnalysis(Analysis analysis) {
		this.analysis = analysis;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public UserInfo getSource() {
		if(source==null){
			source=new UserInfo();
		}
		return source;
	}

	public void setSource(UserInfo source) {
		this.source = source;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<UserInfo> getUserMentions() {
		if(userMentions==null){
			userMentions=new ArrayList<>();
		}
		return userMentions;
	}

	public void setUserMentions(List<UserInfo> userMentions) {
		this.userMentions = userMentions;
	}

	public MediaEntity getMediaEntity() {
		if (mediaEntity == null) {
			mediaEntity = new MediaEntity();
		}
		return mediaEntity;
	}

	public void setMediaEntity(MediaEntity mediaEntity) {
		this.mediaEntity = mediaEntity;
	}

	public UserInfo getTarget() {
		if(target==null){
			target= new UserInfo();
		}
		return target;
	}

	public void setTarget(UserInfo target) {
		this.target = target;
	}

	public Location getLocation() {
		if(location==null){
			location= new Location();
		}
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public List<Place> getPlaces() {
		return places;
	}

	public void setPlaces(List<Place> places) {
		this.places = places;
	}

	public long getLikesCount() {
		return likesCount;
	}

	public void setLikesCount(long likesCount) {
		this.likesCount = likesCount;
	}

	public long getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(long commentCount) {
		this.commentCount = commentCount;
	}

	public long getShareCount() {
		return shareCount;
	}

	public void setShareCount(long shareCount) {
		this.shareCount = shareCount;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public List<NamedEntity> getEntities() {
		return entities;
	}

	public void setEntities(List<NamedEntity> entities) {
		this.entities = entities;
	}

	public List<HashTag> getHashTags() {
		if(HashTags==null){
			HashTags=new ArrayList<>();
		}
		return HashTags;
	}

	public void setHashTags(List<HashTag> hashTags) {
		HashTags = hashTags;
	}

	public String getStory() {
		return story;
	}

	public void setStory(String story) {
		this.story = story;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEventAction() {
		return eventAction;
	}

	public void setEventAction(String eventAction) {
		this.eventAction = eventAction;
	}

	public long getReTweetedCount() {
		return reTweetedCount;
	}

	public void setReTweetedCount(long reTweetedCount) {
		this.reTweetedCount = reTweetedCount;
	}

}
