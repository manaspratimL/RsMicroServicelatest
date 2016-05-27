package com.rs.ms.fb.controllers;

import com.restfb.types.Comment;
import com.restfb.types.Event;
import com.restfb.types.Page;
import com.restfb.types.Post;
import com.rs.ms.fb.models.FacebookPageModel;
import com.rs.ms.fb.service.base.FacebookPageDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.Future;

/**
 * 
 * @author ManasC
 *
 */
@RestController
public class FacebookPageController {

	@Autowired
    FacebookPageDataService fbPageDataService;

	@RequestMapping("/page")
	public String index() {
		return "Greetings from page!";
	}

	@RequestMapping(value = "/pageInfo", method = RequestMethod.GET)
	public Page getPageInfo(String token) {

		return fbPageDataService.getPageInfo(token);
	}

	@RequestMapping(value = "/pageFeeds", method = RequestMethod.GET)
	public List<Post> getPageFeed(@RequestParam(value = "token") String token) {
		return fbPageDataService.getPageFeed(token);
	}

	@RequestMapping(value = "/getPagePostComments", method = RequestMethod.GET)
	public List<Comment> getPagePostComments(@RequestParam(value = "token") String token,
			@RequestParam(value = "postID") String postID) {

		return fbPageDataService.getPostCommentsAndLikes(token, postID);
	}

	@RequestMapping(value = "/pageAll", method = RequestMethod.GET)
	public String getPageAll(@RequestParam(value = "token") String token) throws Exception {
		
		/*Future<FacebookPageModel> page1=fbPageDataService.getPageAll(token);
		
		System.out.println(page1.isDone());*/
		
		return "Processing";
	}
	
	@RequestMapping(value = "/getEvents", method = RequestMethod.GET)
	public List<Event> getPageEvents(@RequestParam(value = "token") String token) {

		return fbPageDataService.getPageEvents(token);
	}
	
	@RequestMapping(value = "/getEventFeeds", method = RequestMethod.GET)
	public List<Post> getEventFeeds(@RequestParam(value="token") String token,@RequestParam(value="eventID") String eventID){
		
		return fbPageDataService.getEventFeed(token, eventID);
	}
	
	@RequestMapping(value = "/getEventDetails", method = RequestMethod.GET)
	public Event getEventDetails(@RequestParam(value="token") String token,@RequestParam(value="eventID") String eventID){
		return fbPageDataService.getEventDetails(token, eventID);
	}
	
	@RequestMapping(value = "/pageSubscribe", method = RequestMethod.GET)
	public Event pageSubscribe(@RequestParam(value="token") String token,@RequestParam(value="eventID") String eventID){
		return fbPageDataService.getEventDetails(token, eventID);
	}
	
}
