package com.rs.ms.in.service.base;

import java.util.HashMap;
import java.util.Map;

public interface InstagramWebhookService {
	public Map<String,String> userToken= new HashMap<>();
	
	public void processChange(String jsonArray)throws Exception;
	
	public void processNewPost(String jsonArray) throws Exception;
}
	