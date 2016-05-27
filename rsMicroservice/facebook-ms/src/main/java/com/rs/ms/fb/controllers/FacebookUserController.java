/**
 * 
 */
package com.rs.ms.fb.controllers;

import java.util.List;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.restfb.scope.ScopeBuilder;
import com.restfb.scope.UserDataPermissions;
import com.restfb.types.Comment;
import com.restfb.types.Post;
import com.restfb.types.User;
import com.rs.ms.fb.models.FacebookUserModel;
import com.rs.ms.fb.service.base.FacebookUserDataService;

/**
 * @author ManasC
 *
 */

@RestController
public class FacebookUserController {

	@Autowired
	private FacebookUserDataService facebookUserDataService;

	@RequestMapping("/")
	public String index() {
		return "Greetings from FacebookController!";
	}

	@RequestMapping(value = "/userInfo", method = RequestMethod.GET)
	public User getUserData(@RequestParam(value = "token") String token) {
		return facebookUserDataService.getUserInfo(token);
	}

	@RequestMapping(value = "/userFeeds", method = RequestMethod.GET)
	public List<Post> getUserFeed(@RequestParam(value = "token") String token,@RequestParam(value = "limit") int limit,@RequestParam(value = "pagination") boolean pagination) {
		return facebookUserDataService.getUserFeed(token,limit,pagination);
	}

	@RequestMapping(value = "/getPostComments", method = RequestMethod.GET)
	public List<Comment> getPostComments(@RequestParam(value = "token") String token,
			@RequestParam(value = "postID") String postID) {
		
		return facebookUserDataService.getPostCommentsAndLikes(token,postID);
	}
	
	@RequestMapping(value = "/userAll", method = RequestMethod.GET)
	public String getUserAll(@RequestParam(value = "token") String token,@RequestParam(value = "fbUserID") String fbUserID) throws Exception {
		
		/*Future<FacebookUserModel> page1=facebookUserDataService.getUserAll(token,fbUserID);
		
		System.out.println(page1.isDone());*/
		
		return "Processing";
	}
	
	@RequestMapping(value = "/getLoginUrl", method = RequestMethod.GET)
	public String getLoginUrl() {
		ScopeBuilder scopeBuilder = new ScopeBuilder();
		scopeBuilder.addPermission(UserDataPermissions.USER_STATUS);
		scopeBuilder.addPermission(UserDataPermissions.USER_ABOUT_ME);
		FacebookClient client = new DefaultFacebookClient(Version.VERSION_2_6);
		String loginDialogUrlString = client.getLoginDialogUrl("230136383995486","http://localhost:3000/auth/facebook", scopeBuilder);
		
		return loginDialogUrlString;
	}

}
