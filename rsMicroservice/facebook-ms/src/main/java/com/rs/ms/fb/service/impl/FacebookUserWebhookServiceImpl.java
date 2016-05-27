package com.rs.ms.fb.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restfb.types.Post;
import com.restfb.types.webhook.WebhookEntry;
import com.restfb.types.webhook.WebhookObject;
import com.rs.ms.fb.models.FbWebhookEntry;
import com.rs.ms.fb.models.FbWebhookObject;
import com.rs.ms.fb.models.Value;
import com.rs.ms.fb.service.base.FacebookUserDataService;
import com.rs.ms.fb.service.base.FacebookUserWebhookService;

/**
 * 
 * @author ManasC
 *
 */
@Service
public class FacebookUserWebhookServiceImpl implements FacebookUserWebhookService {
	
	@Autowired
    FacebookUserDataService fbUserDataService;
	/**
	 * 
	 */
	public void processFieldChanges(WebhookObject webhookObject){
		
		for(WebhookEntry entry:webhookObject.getEntryList()){
			processChange(entry);
		}
	}
	/**
	 * 
	 * @param entry
	 */
	private void processChange(WebhookEntry entry){
		
		System.out.println("ID : "+entry.getId());
		System.out.println("changed fields : "+entry.getChangedFields());
		System.out.println("Changes : "+entry.getChanges());
		
		for(String changeField : entry.getChangedFields()){
			getChanges(changeField,entry);
		}
		
	}
	
	/**
	 * 
	 * @param change
	 * @param fbUserID
	 */
	private FbWebhookObject getChanges(String change,WebhookEntry entry){
		
		System.out.println("Fetching Changes");
		
		FbWebhookObject fbWebObject= new FbWebhookObject();
		fbWebObject.setObject("user");
				
		switch (change) {
		case "feed":
			String token=FacebookUserDataService.Usertokens.get(entry.getId());
			List<Post> posts=fbUserDataService.getUserFeed(token,1,false);
			for(Post post : posts){
				System.out.println(post.getMessage());
				System.out.println(post.getStory());
				
				FbWebhookEntry fbEntry=createWebHookObject(post, entry);
				//fbWebObject.getEntryList().add(fbEntry);
			}
			break;

		default:
			break;
		}
		
		return fbWebObject;
	}
	
	private FbWebhookEntry createWebHookObject(Post post,WebhookEntry entry){
		
		com.rs.ms.fb.models.Change change= new com.rs.ms.fb.models.Change();
		change.setField("feed");
		
		Value value= new Value();
		value.setPost_id(post.getId());
		value.setMessage(post.getMessage()==null?post.getStory():post.getMessage());
		value.setSender_name(post.getFrom()==null?" ":post.getFrom().getName());
		value.setSender_id(post.getFrom()==null?" ":post.getFrom().getId());
		
		change.setValue(value);
		
		FbWebhookEntry fbEntry=new FbWebhookEntry();
		fbEntry.getChanges().add(change);
		
		
		
		return fbEntry;
		
	}
}
