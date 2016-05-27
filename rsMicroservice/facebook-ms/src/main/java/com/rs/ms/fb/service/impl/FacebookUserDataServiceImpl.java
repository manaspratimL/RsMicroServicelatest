package com.rs.ms.fb.service.impl;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.restfb.Connection;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.FacebookClient.DebugTokenInfo;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.json.JsonObject;
import com.restfb.types.Comment;
import com.restfb.types.NamedFacebookType;
import com.restfb.types.Page;
import com.restfb.types.Post;
import com.restfb.types.User;
import com.rs.ms.common.dataModels.RsStream;
import com.rs.ms.common.dataModels.RsStreams;
import com.rs.ms.common.kafka.KafkaProducerService;
import com.rs.ms.fb.models.FacebookConfigModel;
import com.rs.ms.fb.models.FacebookUserModel;
import com.rs.ms.fb.models.FbComment;
import com.rs.ms.fb.models.FbLike;
import com.rs.ms.fb.models.FbPost;
import com.rs.ms.fb.models.PageAccounts;
import com.rs.ms.fb.service.base.FacebookClientService;
import com.rs.ms.fb.service.base.FacebookPageDataService;
import com.rs.ms.fb.service.base.FacebookUserDataService;
import com.rs.ms.fb.utils.DataUrls;

/**
 * 
 * @author ManasC
 *
 */
@Service
public class FacebookUserDataServiceImpl implements FacebookUserDataService {

	@Autowired
	FacebookClientService facebookClientService;

	@Autowired
	KafkaProducerService kafkaProducer;

	@Autowired
	FacebookPageDataService fbPageDataService;

	private FacebookClient facebookClient;

	@Override
	@Async
	public void processUser(FacebookConfigModel config) throws Exception {
		String token = null;

		try {
			if (!config.isOld()) {
				token = getLongLivedAccessToken(config);
				config.setToken(token);
			} else if (isNearExpiry(config.getToken())) {
				token = exchangeLongLivedAccessToken(config);
				config.setToken(token);
			}

		} catch (Exception e) {
			System.out.println("Error : Prompt for login");
			e.printStackTrace();

			token = exchangeLongLivedAccessToken(config);
			config.setToken(token);
		}

		User user = getUserInfo(config.getToken());

		getUserAll(config.getToken(), config);

		PageAccounts pageAccount = fbPageDataService.getPageAccounts(config.getToken());

		Page page = fbPageDataService.getPageInfo(pageAccount.getAccess_token());

		fbPageDataService.subscribePage(pageAccount.getAccess_token(), page.getId());

		fbPageDataService.getPageAll(pageAccount);
	}

	/**
	 * 
	 * @param token
	 * @return
	 */
	private boolean isNearExpiry(String token) {
		facebookClient = facebookClientService.getFacebookClient(token, Version.VERSION_2_6);

		DebugTokenInfo debugTokenInfo = facebookClient.debugToken(token);

		Date currentDate = new Date();
		currentDate.setTime((long) System.currentTimeMillis() * 1000);

		// To calculate the days difference between two dates
		int diffInDays = (int) ((currentDate.getTime() - debugTokenInfo.getExpiresAt().getTime())
				/ (1000 * 60 * 60 * 24));

		if (diffInDays < 3) {
			return true;
		}

		return false;
	}

	/**
	 * 
	 * @param token
	 * @param machineId
	 * @throws Exception
	 */
	private String exchangeLongLivedAccessToken(FacebookConfigModel config) throws Exception {

		String token = config.getToken();

		facebookClient = facebookClientService.getFacebookClient(token, Version.VERSION_2_6);

		JsonObject client_code = facebookClient.publish("oauth/client_code?access_token=" + token, JsonObject.class,
				Parameter.with("client_secret", "f74ac6f816ff3e2d9da35ef18447d9c8"),
				Parameter.with("redirect_uri", "http://localhost:8081/auth/facebook"),
				Parameter.with("client_id", "230136383995486"));

		String code = client_code.getString("code");

		JsonObject access_token = null;
		if (config.getMachineId() != null) {
			access_token = facebookClient.fetchObject("oauth/access_token", JsonObject.class,
					Parameter.with("code", code), Parameter.with("machine_id", config.getMachineId()),
					Parameter.with("redirect_uri", "http://localhost:8081/auth/facebook"),
					Parameter.with("client_id", "230136383995486"));
		} else {

			access_token = facebookClient.fetchObject("oauth/access_token", JsonObject.class,
					Parameter.with("code", code), Parameter.with("redirect_uri", "http://localhost:8081/auth/facebook"),
					Parameter.with("client_id", "230136383995486"));
		}

		String newAccessToken = access_token.getString("access_token");
		config.setToken(newAccessToken);

		String machine_id = access_token.getString("machine_id");
		config.setMachineId(machine_id);

		// publish to kafka
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		String json = gson.toJson(config);
		System.out.println(json);

		kafkaProducer.publishUpdates(json, "fbConfig");

		return newAccessToken;

	}

	/**
	 * 
	 * @param token
	 * @return
	 * @throws Exception
	 */
	private String getLongLivedAccessToken(FacebookConfigModel config) throws Exception {

		facebookClient = facebookClientService.getFacebookClient(config.getToken(), Version.VERSION_2_6);

		AccessToken accessToken = facebookClient.obtainExtendedAccessToken("230136383995486",
				"f74ac6f816ff3e2d9da35ef18447d9c8", config.getToken());

		System.out.println("My extended access token: " + accessToken);

		config.setToken(accessToken.getAccessToken());
		config.setOld(true);

		// publish to kafka
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		String json = gson.toJson(config);
		System.out.println(json);

		kafkaProducer.publishUpdates(json, "fbConfig");

		return accessToken.getAccessToken();
	}

	@Override
	public User getUserInfo(String token) {
		User user = null;
		facebookClient = facebookClientService.getFacebookClient(token, Version.VERSION_2_6);
		try {
			user = facebookClient.fetchObject("/me", User.class, Parameter.with("fields", DataUrls.me));
		} catch (FacebookOAuthException e) {
			e.printStackTrace();
		}

		return user;
	}

	@Async
	public Future<RsStreams> getUserAll(String token, FacebookConfigModel config) throws Exception {

		int limit = 1;

		String userId = Usertokens.get(config.getFacebookId());

		if (userId == null) {
			Usertokens.put(config.getFacebookId(), token);
		}

		facebookClient = facebookClientService.getFacebookClient(token, Version.VERSION_2_6);

		Connection<Post> myFeed = facebookClient.fetchConnection("me/feed", Post.class, Parameter.with("limit", 10));
		int i = 0;
		// Latest Model Algo
		RsStreams rsStreams = new RsStreams();

		for (List<Post> myFeedConnectionPage : myFeed) {

			for (Post post : myFeedConnectionPage) {
				RsStream stream = createRsStreamObjFromPost(post);

				rsStreams.getRsStream().add(stream);

				populateLikeAsRsStreamObj(config, rsStreams, post);

				populateCommentsAsRsStreamObj(config, rsStreams, post);

			}

			i++;
			if (i >= limit) {
				break;
			}

		}

		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		String json = gson.toJson(rsStreams);
		System.out.println(json);

		// produce to kafka
		kafkaProducer.publishUpdates(json, "rsmsfb");

		return new AsyncResult<RsStreams>(rsStreams);
	}

	@Override
	public List<Post> getUserFeed(String token, int limitValue, boolean pagination) {

		facebookClient = facebookClientService.getFacebookClient(token, Version.VERSION_2_6);

		Connection<Post> myFeed = facebookClient.fetchConnection("me/feed", Post.class,
				Parameter.with("limit", limitValue));

		List<Post> allFeeds = new ArrayList<Post>();

		if (pagination) {
			for (List<Post> myFeedConnectionPage : myFeed) {
				allFeeds.addAll(myFeedConnectionPage);
			}
		} else {
			allFeeds.addAll(myFeed.getData());
		}

		return allFeeds;
	}

	@Override
	public List<Comment> getPostCommentsAndLikes(String token, String postID) {

		if (facebookClient == null) {
			facebookClient = facebookClientService.getFacebookClient(token, Version.VERSION_2_6);
		}

		List<Comment> allPostComment = new ArrayList<Comment>();

		Connection<Comment> allComments = facebookClient.fetchConnection(postID + "/comments", Comment.class,
				Parameter.with("fields", DataUrls.commentData));

		for (List<Comment> postcomments : allComments) {

			allPostComment.addAll(postcomments);
		}

		return allPostComment;
	}

	/**
	 * 
	 * @param post
	 * @return
	 */
	private FbPost createFbPostObject(Post post) {
		FbPost fbPost = new FbPost();

		fbPost.setCaption(post.getCaption());
		fbPost.setComment_count(post.getCommentsCount());
		fbPost.setCreatedTime(post.getCreatedTime());
		fbPost.setDescription(post.getDescription());
		fbPost.setFrom(post.getFrom() == null ? " " : post.getFrom().getName());
		fbPost.setUserId(post.getFrom() == null ? " " : post.getFrom().getId());
		fbPost.setUserName(post.getFrom() == null ? " " : post.getFrom().getName());
		fbPost.setId(post.getId());
		fbPost.setLikes_count(post.getLikesCount() == null ? 0 : post.getLikesCount());
		fbPost.setLink(post.getLink());
		fbPost.setMessage(post.getMessage());
		fbPost.setMetadata(post.getMetadata() == null ? " " : post.getMetadata().toString());
		fbPost.setReactions(post.getReactions() == null ? " " : post.getReactions().getViewerReaction());
		fbPost.setShare_count(post.getSharesCount());
		fbPost.setStory(post.getStory());

		return fbPost;
	}

	/**
	 * 
	 * @param post
	 */
	private void populatePostCommentsAndLikes(String token, FbPost post) {

		List<Comment> comments = getPostCommentsAndLikes(token, post.getId());

		for (Comment comment : comments) {
			FbComment fbComment = new FbComment();
			fbComment.setComment_count(comment.getCommentCount());
			fbComment.setCreatedTime(comment.getCreatedTime());
			fbComment.setId(comment.getId());
			fbComment.setLikes_count(comment.getLikeCount() == null ? 0 : comment.getLikeCount());
			fbComment.setMessage(comment.getMessage());
			fbComment.setUserId(comment.getFrom() == null ? " " : comment.getFrom().getId());
			fbComment.setUserName(comment.getFrom() == null ? " " : comment.getFrom().getName());

			if (comment.getLikes() != null) {
				createFbLikeObj(fbComment, comment);
			}

			if (comment.getComments() != null) {
				createFbCommentObj(fbComment, comment);
			}
			post.getComments().add(fbComment);
		}

		List<NamedFacebookType> likes = getPostLike(token, post);

		for (NamedFacebookType like : likes) {
			FbLike fblike = new FbLike();
			fblike.setUserId(like.getId());
			fblike.setUserName(like.getName());
			post.getLikes().add(fblike);
		}

	}

	private List<NamedFacebookType> getPostLike(String token, FbPost post) {

		List<NamedFacebookType> allPostLike = new ArrayList<NamedFacebookType>();

		if (facebookClient == null) {
			facebookClient = facebookClientService.getFacebookClient(token, Version.VERSION_2_6);
		}

		Connection<NamedFacebookType> allLikes = facebookClient.fetchConnection(post.getId() + "/likes",
				NamedFacebookType.class);

		for (List<NamedFacebookType> postlike : allLikes) {

			allPostLike.addAll(postlike);
		}

		return allPostLike;
	}

	private void createFbLikeObj(FbComment parentfbComment, Comment originalComment) {
		List<NamedFacebookType> likes = originalComment.getLikes().getData();

		for (NamedFacebookType user : likes) {
			FbLike like = new FbLike();
			like.setUserName(user.getName());
			like.setUserId(user.getId());
			parentfbComment.getLikes().add(like);
		}
	}

	private void createFbCommentObj(FbComment parentfbComment, Comment originalComment) {

		List<Comment> comments = originalComment.getComments().getData();

		for (Comment comment : comments) {
			FbComment fbComment = new FbComment();
			fbComment.setComment_count(comment.getCommentCount());
			fbComment.setCreatedTime(comment.getCreatedTime());
			fbComment.setId(comment.getId());
			fbComment.setLikes_count(comment.getLikeCount() == null ? 0 : comment.getLikeCount());
			fbComment.setMessage(comment.getMessage());
			fbComment.setUserId(comment.getFrom() == null ? " " : comment.getFrom().getId());
			fbComment.setUserName(comment.getFrom() == null ? " " : comment.getFrom().getName());

			if (comment.getLikes() != null) {
				createFbLikeObj(fbComment, comment);
			}

			parentfbComment.getComments().add(fbComment);
		}
	}

	private void writeToFile(FacebookUserModel model) {

		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		try {
			gson.toJson(model, new FileWriter("D:\\Activiti\\facebookUserModel.json"));
		} catch (JsonIOException | IOException e) {
			e.printStackTrace();
		}

	}

	// ############New model creation logic##################
	/**
	 * Commments of post
	 * 
	 * @param post
	 * @return
	 */
	private RsStream createRsStreamObjFromPost(Post post) {
		RsStream stream = new RsStream();

		stream.setEventType("POST");
		stream.getMediaEntity().setCaption(post.getCaption());
		stream.setCreatedTime(post.getCreatedTime());
		stream.setContent(post.getMessage());
		stream.setStory(post.getStory());
		stream.getMediaEntity().setText(post.getDescription());
		stream.getMediaEntity().setMediaUrl(post.getLink());

		stream.setCommentCount(post.getCommentsCount());
		stream.setShareCount(post.getSharesCount());
		stream.setLikesCount(post.getLikesCount() == null ? 0 : post.getLikesCount());

		stream.setSourceId(post.getId());
		stream.setParentSourceId(post.getParentId());
		stream.getSourceInfo().setName("FACEBOOK");
		stream.getSourceInfo().setInteractionType("PUBLIC");

		stream.getSource().setFullName(post.getFrom() == null ? null : post.getFrom().getName());
		stream.getSource().setUserId(post.getFrom() == null ? null : post.getFrom().getId());

		return stream;
	}

	/**
	 * 
	 * @param config
	 * @param rsStream
	 * @param post
	 */
	private void populateCommentsAsRsStreamObj(FacebookConfigModel config, RsStreams rsStream, Post post) {
		List<Comment> comments = getPostCommentsAndLikes(config.getToken(), post.getId());

		for (Comment comment : comments) {

			RsStream stream = createCommentRsStreamObj(comment, post);

			rsStream.getRsStream().add(stream);

			// Get Likes for the Comment
			if (comment.getLikes() != null) {
				List<NamedFacebookType> likes = comment.getLikes().getData();

				for (NamedFacebookType like : likes) {
					RsStream likestream = createLikeRsStreamObj(like, post);
					rsStream.getRsStream().add(likestream);
				}
			}

			// Get replies for the comment
			if (comment.getComments() != null) {
				List<Comment> secondlevelcomments = comment.getComments().getData();

				for (Comment reply : secondlevelcomments) {

					RsStream commentstream = createCommentRsStreamObj(reply, post);
					rsStream.getRsStream().add(commentstream);
				}
			}

		}
	}

	/**
	 * Likes of post
	 * 
	 * @param config
	 * @param rsStreams
	 * @param post
	 */
	private void populateLikeAsRsStreamObj(FacebookConfigModel config, RsStreams rsStreams, Post post) {

		if (facebookClient == null) {
			facebookClient = facebookClientService.getFacebookClient(config.getToken(), Version.VERSION_2_6);
		}

		Connection<NamedFacebookType> allLikes = facebookClient.fetchConnection(post.getId() + "/likes",
				NamedFacebookType.class);

		for (List<NamedFacebookType> postlike : allLikes) {

			for (NamedFacebookType like : postlike) {
				RsStream stream = createLikeRsStreamObj(like, post);
				rsStreams.getRsStream().add(stream);
			}
		}

	}

	/**
	 * 
	 * @param comment
	 * @param post
	 * @return
	 */
	private RsStream createCommentRsStreamObj(Comment comment, Post post) {
		RsStream stream = new RsStream();

		stream.setEventType("COMMENT");
		stream.setSourceId(comment.getId());
		stream.setParentSourceId(post.getId());
		stream.getSourceInfo().setName("FACEBOOK");
		stream.getSourceInfo().setInteractionType("PUBLIC");

		stream.setContent(comment.getMessage());
		stream.setCreatedTime(comment.getCreatedTime());
		stream.setCommentCount(comment.getCommentCount());
		stream.setLikesCount(comment.getLikeCount() == null ? 0 : comment.getLikeCount());

		stream.getSource().setFullName(comment.getFrom() == null ? " " : comment.getFrom().getName());
		stream.getSource().setUserId(comment.getFrom() == null ? " " : comment.getFrom().getId());

		return stream;
	}

	/**
	 * 
	 * @param like
	 * @param post
	 * @return
	 */
	private RsStream createLikeRsStreamObj(NamedFacebookType like, Post post) {

		RsStream stream = new RsStream();

		stream.setEventType("LIKE");
		stream.setParentSourceId(post.getId());
		stream.getSourceInfo().setName("FACEBOOK");
		stream.getSourceInfo().setInteractionType("PUBLIC");

		stream.getSource().setFullName(like.getName());
		stream.getSource().setUserId(like.getId());

		return stream;
	}

}
