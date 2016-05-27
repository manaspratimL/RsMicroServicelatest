package com.rs.ms.in.service.base;

import java.util.List;

import org.jinstagram.Instagram;
import org.jinstagram.entity.comments.CommentData;
import org.jinstagram.entity.common.User;
import org.jinstagram.entity.media.MediaInfoFeed;
import org.jinstagram.entity.relationships.RelationshipData;
import org.jinstagram.entity.tags.TagInfoData;
import org.jinstagram.entity.users.feed.MediaFeedData;
import org.jinstagram.entity.users.feed.UserFeedData;
import org.jinstagram.exceptions.InstagramException;

import com.rs.ms.in.models.InstagramConfigModel;

public interface InstagramUserService {

	public void processUser(InstagramConfigModel inConfigModel) throws Exception;

	public MediaInfoFeed getMedia(Instagram instagram, String mediaId) throws InstagramException;

	public List<CommentData> getAllMediaComments(Instagram instagram, String mediaId) throws InstagramException;
	
	public List<User> getAllMediaLikes(Instagram instagram, String mediaId) throws InstagramException;
	
	public List<TagInfoData> searchTag(Instagram instagram, String tag) throws InstagramException;
	
	public List<MediaFeedData> getRecentMediaTags(Instagram instagram, String tagName) throws InstagramException;
	
	public RelationshipData getUserRelationship(Instagram instagram, String userIdToCheck) throws InstagramException;
	
	public List<MediaFeedData>  getUserLikedMediaFeed(Instagram instagram) throws InstagramException;
	
	public List<MediaFeedData> getUserFeeds(Instagram instagram, String userId) throws InstagramException;
	
	public List<UserFeedData> getUserFollowing(Instagram instagram, String userId) throws InstagramException ;
	
	public List<UserFeedData> getUserFollowedByList(Instagram instagram, String userId) throws InstagramException;
	
	

}
