package com.rs.ms.fb.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FbPost{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String metadata;
	private String story;
	private String reactions;
	private String from;
	private String userName;
	private String userId;
	private String message;
	private String link;
	private String description;
	private String caption;
	
	private Long likes_count;
	private Long comment_count;
	private Long share_count;
	
	private Date updatedTime;
	private Date createdTime;
	
	private List<FbComment> comments;
	private List<FbLike> likes;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the metadata
	 */
	public String getMetadata() {
		return metadata;
	}
	/**
	 * @param metadata the metadata to set
	 */
	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}
	/**
	 * @return the story
	 */
	public String getStory() {
		return story;
	}
	/**
	 * @param story the story to set
	 */
	public void setStory(String story) {
		this.story = story;
	}
	/**
	 * @return the reactions
	 */
	public String getReactions() {
		return reactions;
	}
	/**
	 * @param reactions the reactions to set
	 */
	public void setReactions(String reactions) {
		this.reactions = reactions;
	}
	/**
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}
	/**
	 * @param from the from to set
	 */
	public void setFrom(String from) {
		this.from = from;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}
	/**
	 * @param link the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the caption
	 */
	public String getCaption() {
		return caption;
	}
	/**
	 * @param caption the caption to set
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}
	/**
	 * @return the likes_count
	 */
	public long getLikes_count() {
		return likes_count;
	}
	/**
	 * @param likes_count the likes_count to set
	 */
	public void setLikes_count(long likes_count) {
		this.likes_count = likes_count;
	}
	/**
	 * @return the comment_count
	 */
	public long getComment_count() {
		return comment_count;
	}
	/**
	 * @param comment_count the comment_count to set
	 */
	public void setComment_count(long comment_count) {
		this.comment_count = comment_count;
	}
	/**
	 * @return the share_count
	 */
	public long getShare_count() {
		return share_count;
	}
	/**
	 * @param share_count the share_count to set
	 */
	public void setShare_count(long share_count) {
		this.share_count = share_count;
	}
	/**
	 * @return the updatedTime
	 */
	public Date getUpdatedTime() {
		return updatedTime;
	}
	/**
	 * @param updatedTime the updatedTime to set
	 */
	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}
	/**
	 * @return the createdTime
	 */
	public Date getCreatedTime() {
		return createdTime;
	}
	/**
	 * @param createdTime the createdTime to set
	 */
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	/**
	 * @return the comments
	 */
	public List<FbComment> getComments() {
		if(comments==null){
			comments=new ArrayList<>();
		}
		return comments;
	}
	/**
	 * @param comments the comments to set
	 */
	public void setComments(List<FbComment> comments) {
		this.comments = comments;
	}
	/**
	 * @return the likes
	 */
	public List<FbLike> getLikes() {
		if(likes==null){
			likes=new ArrayList<>();
		}
		return likes;
	}
	/**
	 * @param likes the likes to set
	 */
	public void setLikes(List<FbLike> likes) {
		this.likes = likes;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	

}
