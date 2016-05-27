package com.rs.ms.in.controllers;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rs.ms.in.service.base.InstagramUserService;

@RestController
public class InstagramUserController {

	@Autowired
	private InstagramUserService instaUserDataService;

	@RequestMapping("/insta")
	public String index() {
		return "Greetings from InstagramController!";
	}

	@RequestMapping(value = "/getAllMediaComments", method = RequestMethod.GET)
	public  List<CommentData> getAllMediaComments(@RequestParam(value = "token") String token,
			@RequestParam(value = "secret") String secret, @RequestParam(value = "mediaId") String mediaId) throws InstagramException {
		
		Instagram instagram= new Instagram(token, secret);
		return instaUserDataService.getAllMediaComments(instagram, mediaId);
	}

	@RequestMapping(value = "/getAllMediaLikes", method = RequestMethod.GET)
	public  List<User> getAllMediaLikes(@RequestParam(value = "token") String token,
			@RequestParam(value = "secret") String secret, @RequestParam(value = "mediaId") String mediaId) throws InstagramException {
		
		Instagram instagram= new Instagram(token, secret);
		return instaUserDataService.getAllMediaLikes(instagram, mediaId);
	}

	@RequestMapping(value = "/searchTag", method = RequestMethod.GET)
	public List<TagInfoData> searchTag(@RequestParam(value = "token") String token,
			@RequestParam(value = "secret") String secret, @RequestParam(value = "tag") String tag) throws InstagramException {

		Instagram instagram= new Instagram(token, secret);
		return instaUserDataService.searchTag(instagram, tag);
	}

	@RequestMapping(value = "/getRecentMediaTags", method = RequestMethod.GET)
	public List<MediaFeedData> getRecentMediaTags(@RequestParam(value = "token") String token,
			@RequestParam(value = "secret") String secret, @RequestParam(value = "tag") String tag) throws Exception {
		Instagram instagram= new Instagram(token, secret);
		return instaUserDataService.getRecentMediaTags(instagram, tag);
	}

	@RequestMapping(value = "/getUserRelationship", method = RequestMethod.GET)
	public RelationshipData getUserRelationship(@RequestParam(value = "token") String token,
			@RequestParam(value = "secret") String secret, @RequestParam(value = "userIdToCheck") String userIdToCheck) throws InstagramException {
		
		Instagram instagram= new Instagram(token, secret);
		return instaUserDataService.getUserRelationship(instagram, userIdToCheck);
	}

	@RequestMapping(value = "/getUserLikedMediaFeed", method = RequestMethod.GET)
	public  List<MediaFeedData> getUserLikedMediaFeed(@RequestParam(value = "token") String token,
			@RequestParam(value = "secret") String secret) throws InstagramException {
		Instagram instagram= new Instagram(token, secret);
		return instaUserDataService.getUserLikedMediaFeed(instagram);
	}

	@RequestMapping(value = "/getUserFeeds", method = RequestMethod.GET)
	public  List<MediaFeedData> getUserFeeds(@RequestParam(value = "token") String token,
			@RequestParam(value = "secret") String secret, @RequestParam(value = "userId") String userId) throws InstagramException {
		
		Instagram instagram= new Instagram(token, secret);
		return instaUserDataService.getUserFeeds(instagram, userId);
	}

	@RequestMapping(value = "/getUserFollowing", method = RequestMethod.GET)
	public  List<UserFeedData> getUserFollowing(@RequestParam(value = "token") String token,
			@RequestParam(value = "secret") String secret, @RequestParam(value = "userId") String userId) throws Exception {

		Instagram instagram= new Instagram(token, secret);
		return instaUserDataService.getUserFollowing(instagram, userId);
	}

	@RequestMapping(value = "/getUserFollowedByList", method = RequestMethod.GET)
	public   List<UserFeedData> getUserFollowedByList(@RequestParam(value = "token") String token,
			@RequestParam(value = "secret") String secret, @RequestParam(value = "userId") String userId) throws InstagramException {
		Instagram instagram= new Instagram(token, secret);
		return instaUserDataService.getUserFollowedByList(instagram, userId);
	}

	@RequestMapping(value = "/getMedia", method = RequestMethod.GET)
	public  MediaInfoFeed getMedia(@RequestParam(value = "token") String token,
			@RequestParam(value = "secret") String secret, @RequestParam(value = "mediaId") String mediaId) throws InstagramException {
		
		Instagram instagram= new Instagram(token, secret);
		return instaUserDataService.getMedia(instagram, mediaId);
	}

}
