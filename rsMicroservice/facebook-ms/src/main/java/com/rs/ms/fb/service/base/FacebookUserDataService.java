package com.rs.ms.fb.service.base;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Future;

import org.springframework.stereotype.Component;

import com.restfb.types.Comment;
import com.restfb.types.Post;
import com.restfb.types.User;
import com.rs.ms.common.dataModels.RsStreams;
import com.rs.ms.fb.models.FacebookConfigModel;

/**
 * 
 * @author ManasC
 *
 */
@Component
public interface FacebookUserDataService {
	
public static HashMap<String,String> Usertokens=new HashMap<>();
	
	public User getUserInfo(String token);
	
	public List<Post> getUserFeed(String token,int limitValue,boolean pagination);
	
	public Future<RsStreams> getUserAll(String token,FacebookConfigModel config)throws Exception;
	
	public List<Comment> getPostCommentsAndLikes(String token,String postID);
	
	public void processUser(FacebookConfigModel config)throws Exception;
}
