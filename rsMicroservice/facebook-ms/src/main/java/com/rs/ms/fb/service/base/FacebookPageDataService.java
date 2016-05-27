package com.rs.ms.fb.service.base;

import java.util.List;
import java.util.concurrent.Future;

import org.springframework.stereotype.Component;

import com.restfb.types.Comment;
import com.restfb.types.Event;
import com.restfb.types.Page;
import com.restfb.types.Post;
import com.rs.ms.common.dataModels.RsStreams;
import com.rs.ms.fb.models.PageAccounts;

/**
 * 
 * @author ManasC
 *
 */
@Component
public interface FacebookPageDataService {
	public Page getPageInfo(String token);

	public List<Post> getPageFeed(String token);

	public List<Comment> getPostCommentsAndLikes(String token, String postID);
	
	public List<Event> getPageEvents(String token);
	
	public Future<RsStreams> getPageAll(PageAccounts token)throws Exception;
	
	public List<Post> getEventFeed(String token, String eventID);
	
	public Event getEventDetails(String token, String eventID);
	
	public PageAccounts getPageAccounts(String token);
	
	public void subscribePage(String userAccessToken,String pageId);
	
}
