package com.rs.ms.in.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.jinstagram.Instagram;
import org.jinstagram.entity.comments.CommentData;
import org.jinstagram.entity.comments.MediaCommentsFeed;
import org.jinstagram.entity.common.Caption;
import org.jinstagram.entity.common.Comments;
import org.jinstagram.entity.common.ImageData;
import org.jinstagram.entity.common.Images;
import org.jinstagram.entity.common.Likes;
import org.jinstagram.entity.common.Location;
import org.jinstagram.entity.common.Pagination;
import org.jinstagram.entity.common.User;
import org.jinstagram.entity.common.UsersInPhoto;
import org.jinstagram.entity.likes.LikesFeed;
import org.jinstagram.entity.media.MediaInfoFeed;
import org.jinstagram.entity.relationships.RelationshipData;
import org.jinstagram.entity.relationships.RelationshipFeed;
import org.jinstagram.entity.tags.TagInfoData;
import org.jinstagram.entity.tags.TagMediaFeed;
import org.jinstagram.entity.tags.TagSearchFeed;
import org.jinstagram.entity.users.basicinfo.UserInfo;
import org.jinstagram.entity.users.feed.MediaFeed;
import org.jinstagram.entity.users.feed.MediaFeedData;
import org.jinstagram.entity.users.feed.UserFeed;
import org.jinstagram.entity.users.feed.UserFeedData;
import org.jinstagram.exceptions.InstagramException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rs.ms.common.dataModels.HashTag;
import com.rs.ms.common.dataModels.RsStream;
import com.rs.ms.common.dataModels.RsStreams;
import com.rs.ms.common.kafka.KafkaProducerService;
import com.rs.ms.in.models.InstagramConfigModel;
import com.rs.ms.in.models.InstagramData;
import com.rs.ms.in.models.InstagramUserModel;
import com.rs.ms.in.service.base.InstagramClientService;
import com.rs.ms.in.service.base.InstagramUserService;
import com.rs.ms.in.service.base.InstagramWebhookService;

@Service
public class InstagramUserServiceImpl implements InstagramUserService {

	@Autowired
	KafkaProducerService kafkaProducer;

	@Autowired
	InstagramWebhookService inWebHookService;

	@Autowired
	InstagramClientService inClientService;

	private DateFormat dfm = new SimpleDateFormat("yyyyMMdd");

	@Override
	public void processUser(InstagramConfigModel inConfigModel) throws Exception {

		Instagram instagram = new Instagram(inConfigModel.getToken(), inConfigModel.getSecret());

		inClientService.setInstagram(instagram);

		inWebHookService.userToken.put(inConfigModel.getInstagramId(), inConfigModel.getToken());

		UserInfo userInfo = instagram.getUserInfo(inConfigModel.getInstagramId());

		// MediaFeed mediaFeed =
		// instagram.getRecentMediaFeed(inConfigModel.getInstagramId(),1,null,null,null,null);

		MediaFeed mediaFeed = instagram.getRecentMediaFeed(inConfigModel.getInstagramId());
		List<MediaFeedData> instaFeed = new ArrayList<MediaFeedData>();
		
		//new model
		RsStreams rsStreams= new RsStreams();
		rsStreams.setProvider("INSTAGRAM");
		rsStreams.setSocialMediaUUID(userInfo.getData().getId());
		getUserAllData(instagram, inConfigModel, mediaFeed,  mediaFeed.getPagination(), rsStreams);
		
		//oldModel logic
		//getUserAll(instagram, inConfigModel, mediaFeed, instaFeed, mediaFeed.getPagination());

		// old model
		// InstagramData inData = createInstagramDataModel(instaFeed, userInfo);

		//RsStreams rsStreams = createRsStreamObject(instaFeed, userInfo);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		String json = gson.toJson(rsStreams);
		System.out.println(json);

		// produce to kafka
		kafkaProducer.publishUpdates(json, "rsmsin");

		// inConfigModel.setInstagramId("2200743519");

		/*
		 * searchTag(instagram, "Kohli");
		 * 
		 * getRecentMediaTags(instagram, "webhookTest");
		 * 
		 * getUserRelationship(instagram, "2200743519");
		 * 
		 * UserInfoData userData = userInfo.getData();
		 * 
		 * getUserFeeds(instagram, inConfigModel.getInstagramId());
		 * 
		 * getUserFollowedByList(instagram, inConfigModel.getInstagramId());
		 * 
		 * getUserFollowing(instagram, inConfigModel.getInstagramId());
		 * 
		 * getUserLikedMediaFeed(instagram);
		 */

	}

	private InstagramData createInstagramDataModel(List<MediaFeedData> instaFeed, UserInfo userInfo) {
		InstagramData data = new InstagramData();
		InstagramUserModel instagramUser = new InstagramUserModel();

		instagramUser.setUserId(userInfo.getData().getId());
		instagramUser.setPost(instaFeed);

		data.setInstagramUser(instagramUser);
		data.setType("FEED");

		return data;

	}

	/**
	 * Extract All Post of the Owner
	 * 
	 * @param instagram
	 * @param inConfigModel
	 * @param instaFeeds
	 * @param page
	 * @throws InstagramException
	 */
	public void getUserAll(Instagram instagram, InstagramConfigModel inConfigModel, MediaFeed mediaFeed,
			List<MediaFeedData> instaFeeds, Pagination page) throws InstagramException {

		List<MediaFeedData> mediaFeeds = mediaFeed.getData();

		for (MediaFeedData mediaData : mediaFeeds) {

			mediaData.getComments().setComments(getAllMediaComments(instagram, mediaData.getId()));

			mediaData.getLikes().setLikesUserList(getAllMediaLikes(instagram, mediaData.getId()));
		}

		instaFeeds.addAll(mediaFeeds);

		if (page.hasNextPage()) {
			MediaFeed mediaFeedNext = instagram.getRecentMediaNextPage(page);
			Pagination nextPage = mediaFeed.getPagination();
			getUserAll(instagram, inConfigModel, mediaFeedNext, instaFeeds, nextPage);
		} else {
			return;
		}

	}

	public List<CommentData> getAllMediaComments(Instagram instagram, String mediaId) throws InstagramException {

		MediaCommentsFeed feed = instagram.getMediaComments(mediaId);
		List<CommentData> comments = feed.getCommentDataList();

		for (CommentData comment : comments) {
			System.out.println("id : " + comment.getId());
			System.out.println("created_time : " + comment.getCreatedTime());
			System.out.println("text : " + comment.getText());

		}

		return comments;

	}

	public List<User> getAllMediaLikes(Instagram instagram, String mediaId) throws InstagramException {
		LikesFeed feed = instagram.getUserLikes(mediaId);

		List<User> users = feed.getUserList();

		for (User user : users) {
			System.out.println("id : " + user.getId());
			System.out.println("full_name : " + user.getFullName());
			System.out.println("user_name : " + user.getUserName());
			System.out.println("profile_picture : " + user.getProfilePictureUrl());
			System.out.println("website : " + user.getWebsiteUrl());
			System.out.println();

		}

		return users;
	}

	public List<TagInfoData> searchTag(Instagram instagram, String tag) throws InstagramException {
		// instagram.getRecentMediaTags(tagName)
		TagSearchFeed searchFeed = instagram.searchTags(tag);

		List<TagInfoData> tags = searchFeed.getTagList();

		for (TagInfoData tagData : tags) {
			System.out.println("name : " + tagData.getTagName());
			System.out.println("media_count : " + tagData.getMediaCount());
			System.out.println();
		}

		return tags;
	}

	public List<MediaFeedData> getRecentMediaTags(Instagram instagram, String tagName) throws InstagramException {

		TagMediaFeed mediaFeed = instagram.getRecentMediaTags(tagName);

		List<MediaFeedData> mediaFeeds = mediaFeed.getData();

		for (MediaFeedData mediaData : mediaFeeds) {
			System.out.println("id : " + mediaData.getId());
			System.out.println("created time : " + mediaData.getCreatedTime());
			System.out.println("link : " + mediaData.getLink());
			System.out.println("tags : " + mediaData.getTags().toString());
			System.out.println("filter : " + mediaData.getImageFilter());
			System.out.println("type : " + mediaData.getType());

			System.out.println("-- Comments --");
			Comments comments = mediaData.getComments();

			System.out.println("-- Caption --");
			Caption caption = mediaData.getCaption();

			System.out.println("-- Likes --");
			Likes likes = mediaData.getLikes();

			System.out.println("-- Images --");
			Images images = mediaData.getImages();

			ImageData lowResolutionImg = images.getLowResolution();
			ImageData stdResolutionImg = images.getStandardResolution();
			ImageData thumbnailImg = images.getThumbnail();

			Location location = mediaData.getLocation();
			System.out.println();
		}
		return mediaFeeds;
	}

	public RelationshipData getUserRelationship(Instagram instagram, String userIdToCheck) throws InstagramException {
		RelationshipFeed relFeed = instagram.getUserRelationship(userIdToCheck);
		RelationshipData relFeeds = relFeed.getData();
		System.out.println(relFeeds.getIncomingStatus());
		return relFeeds;
	}

	public List<MediaFeedData> getUserLikedMediaFeed(Instagram instagram) throws InstagramException {
		MediaFeed mediaFeed = instagram.getUserLikedMediaFeed();
		List<MediaFeedData> mediaFeeds = mediaFeed.getData();
		for (MediaFeedData mediaData : mediaFeeds) {
			System.out.println("id : " + mediaData.getId());
			System.out.println("created time : " + mediaData.getCreatedTime());
			System.out.println("link : " + mediaData.getLink());
			System.out.println("tags : " + mediaData.getTags().toString());
			System.out.println("filter : " + mediaData.getImageFilter());
			System.out.println("type : " + mediaData.getType());

			System.out.println("-- Comments --");
			Comments comments = mediaData.getComments();

			System.out.println("-- Caption --");
			Caption caption = mediaData.getCaption();

			System.out.println("-- Likes --");
			Likes likes = mediaData.getLikes();

			System.out.println("-- Images --");
			Images images = mediaData.getImages();

			ImageData lowResolutionImg = images.getLowResolution();
			ImageData stdResolutionImg = images.getStandardResolution();
			ImageData thumbnailImg = images.getThumbnail();

			Location location = mediaData.getLocation();
			System.out.println();
		}
		return mediaFeeds;
	}

	public List<MediaFeedData> getUserFeeds(Instagram instagram, String userId) throws InstagramException {

		MediaFeed mediaFeed = instagram.getRecentMediaFeed(userId);
		List<MediaFeedData> mediaFeeds = mediaFeed.getData();

		for (MediaFeedData mediaData : mediaFeeds) {
			System.out.println("id : " + mediaData.getId());
			System.out.println("created time : " + mediaData.getCreatedTime());
			System.out.println("link : " + mediaData.getLink());
			System.out.println("tags : " + mediaData.getTags().toString());
			System.out.println("filter : " + mediaData.getImageFilter());
			System.out.println("type : " + mediaData.getType());

			System.out.println("-- Comments --");
			Comments comments = mediaData.getComments();

			System.out.println("-- Caption --");
			Caption caption = mediaData.getCaption();

			System.out.println("-- Likes --");
			Likes likes = mediaData.getLikes();

			System.out.println("-- Images --");
			Images images = mediaData.getImages();

			ImageData lowResolutionImg = images.getLowResolution();
			ImageData stdResolutionImg = images.getStandardResolution();
			ImageData thumbnailImg = images.getThumbnail();

			Location location = mediaData.getLocation();
			System.out.println();
		}
		return mediaFeeds;
	}

	public List<UserFeedData> getUserFollowing(Instagram instagram, String userId) throws InstagramException {
		UserFeed feed = instagram.getUserFollowList(userId);
		List<UserFeedData> users = feed.getUserList();

		for (UserFeedData user : users) {
			System.out.println("id : " + user.getId());
			System.out.println("username : " + user.getUserName());
			System.out.println("first_name : " + user.getFullName());
			System.out.println("profile_picture : " + user.getProfilePictureUrl());
		}

		return users;
	}

	public List<UserFeedData> getUserFollowedByList(Instagram instagram, String userId) throws InstagramException {
		UserFeed feed = instagram.getUserFollowedByList(userId);
		List<UserFeedData> users = feed.getUserList();

		for (UserFeedData user : users) {
			System.out.println("id : " + user.getId());
			System.out.println("username : " + user.getUserName());
			System.out.println("first_name : " + user.getFullName());
			System.out.println("profile_picture : " + user.getProfilePictureUrl());
		}

		return users;
	}

	public MediaInfoFeed getMedia(Instagram instagram, String mediaId) throws InstagramException {
		MediaInfoFeed media = instagram.getMediaInfo(mediaId);

		return media;
	}

	// ############ new model creation
	private RsStreams createRsStreamObject(List<MediaFeedData> instaFeed, UserInfo userInfo) {
		RsStreams rsStreams = new RsStreams();
		rsStreams.setProvider("INSTAGRAM");
		rsStreams.setSocialMediaUUID(userInfo.getData().getId());

		for (MediaFeedData data : instaFeed) {
			RsStream stream = createRsStreamObjfromPost(data);
			rsStreams.getRsStream().add(stream);

			createRsStreamObjfromComment(data.getComments().getComments(), rsStreams, data);
			createRsStreamObjfromLike(data.getLikes().getLikesUserList(), rsStreams, data);

		}
		return rsStreams;
	}

	private RsStream createRsStreamObjfromPost(MediaFeedData post) {
		RsStream stream = new RsStream();

		stream.setEventType("POST");
		stream.getMediaEntity().setCaption(post.getCaption().getText());
		stream.setCreatedTime(timeConversion(post.getCreatedTime()));
		stream.setContent(post.getCaption().getText());
		
		if(post.getType().equalsIgnoreCase("image")){
			stream.getMediaEntity().setMediaUrl(post.getImages().getStandardResolution().getImageUrl());
		}else if(post.getType().equalsIgnoreCase("video")){
			stream.getMediaEntity().setMediaUrl(post.getVideos().getStandardResolution().getUrl());
		}
		

		stream.setCommentCount(post.getComments().getCount());
		stream.setLikesCount(post.getLikes().getCount());

		stream.setSourceId(post.getId());

		stream.getSourceInfo().setName("INSTAGRAM");
		stream.getSourceInfo().setInteractionType("PUBLIC");

		stream.getSource().setFullName(
				post.getCaption().getFrom().getFullName() == null ? null : post.getCaption().getFrom().getFullName());
		stream.getSource()
				.setUserId(post.getCaption().getFrom().getId() == null ? null : post.getCaption().getFrom().getId());
		stream.getSource().setUserName(
				post.getCaption().getFrom().getUsername() == null ? null : post.getCaption().getFrom().getUsername());
		stream.getSource().setProfilePictureUrl(post.getCaption().getFrom().getProfilePicture() == null ? null
				: post.getCaption().getFrom().getProfilePicture());

		// populate hashTag
		for (String tag : post.getTags()) {
			HashTag hashTag = new HashTag();
			hashTag.setTag(tag);
			stream.getHashTags().add(hashTag);
		}

		// user Mentions
		for (UsersInPhoto user : post.getUsersInPhotoList()) {
			com.rs.ms.common.dataModels.UserInfo userInfo = new com.rs.ms.common.dataModels.UserInfo();
			userInfo.setUserId(user.getUser().getId());
			userInfo.setFullName(user.getUser().getFullName());
			userInfo.setUserName(user.getUser().getUserName());
			userInfo.setProfilePictureUrl(user.getUser().getProfilePictureUrl());

			stream.getUserMentions().add(userInfo);
		}

		return stream;
	}

	private void createRsStreamObjfromComment(List<CommentData> comments, RsStreams rsStreams, MediaFeedData data) {

		for (CommentData comment : comments) {

			RsStream stream = new RsStream();
			stream.setSourceId(comment.getId());
			stream.setParentSourceId(data.getId());

			stream.setEventType("COMMENT");
			stream.getSourceInfo().setName("INSTAGRAM");
			stream.getSourceInfo().setInteractionType("PUBLIC");

			stream.setContent(comment.getText());
			stream.getSource().setUserId(comment.getCommentFrom().getId());
			stream.getSource().setUserName(comment.getCommentFrom().getUsername());
			stream.getSource().setFullName(comment.getCommentFrom().getFullName());
			stream.getSource().setProfilePictureUrl(comment.getCommentFrom().getProfilePicture());

			rsStreams.getRsStream().add(stream);
		}

	}

	/**
	 * 
	 * @param likes
	 * @param rsStreams
	 * @param data
	 */
	private void createRsStreamObjfromLike(List<User> users, RsStreams rsStreams, MediaFeedData post) {

		for (User user : users) {
			RsStream stream = new RsStream();

			stream.setEventType("LIKE");
			stream.setParentSourceId(post.getId());
			stream.getSourceInfo().setName("INSTAGRAM");
			stream.getSourceInfo().setInteractionType("PUBLIC");

			stream.getSource().setFullName(user.getFullName());
			stream.getSource().setUserId(user.getId());
			stream.getSource().setUserName(user.getUserName());
			stream.getSource().setProfilePictureUrl(user.getProfilePictureUrl());

			rsStreams.getRsStream().add(stream);
		}

	}

	/**
	 * Extract All Post of the Owner
	 * 
	 * @param instagram
	 * @param inConfigModel
	 * @param instaFeeds
	 * @param page
	 * @throws InstagramException
	 */
	public void getUserAllData(Instagram instagram, InstagramConfigModel inConfigModel, MediaFeed mediaFeed,
			Pagination page, RsStreams rsStreams) throws InstagramException {

		List<MediaFeedData> mediaFeeds = mediaFeed.getData();

		for (MediaFeedData mediaData : mediaFeeds) {

			RsStream stream = createRsStreamObjfromPost(mediaData);

			rsStreams.getRsStream().add(stream);

			MediaCommentsFeed feed = instagram.getMediaComments(mediaData.getId());
			List<CommentData> comments = feed.getCommentDataList();
			createRsStreamObjfromComment(comments, rsStreams, mediaData);

			LikesFeed likefeed = instagram.getUserLikes(mediaData.getId());
			List<User> users = likefeed.getUserList();
			createRsStreamObjfromLike(users, rsStreams, mediaData);
		}


		if (page.hasNextPage()) {
			MediaFeed mediaFeedNext = instagram.getRecentMediaNextPage(page);
			Pagination nextPage = mediaFeed.getPagination();
			getUserAllData(instagram, inConfigModel, mediaFeedNext, nextPage,rsStreams);
		} else {
			return;
		}

	}

	/**
	 * 
	 * @param time
	 * @return
	 */
	private Date timeConversion(String time) {
		Date unixtime = null;
		dfm.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));

		try {
			unixtime = dfm.parse(time);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return unixtime;
	}
}
