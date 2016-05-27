package com.rs.ms.fb.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.rs.ms.common.models.MessageHandler;
import com.rs.ms.fb.models.FacebookConfigModel;
import com.rs.ms.fb.service.base.FacebookUserDataService;

@Service
public class FacebookMessageHandler implements MessageHandler {

	private final FacebookUserDataService fbUserDataService;
	private Gson gson;

	@Autowired
	public FacebookMessageHandler(FacebookUserDataService fbUserDataService) {
		this.fbUserDataService = fbUserDataService;
		this.gson = new Gson();
	}

	@Override
	public void handleMessage(String message) {
		
    	FacebookConfigModel fbConfigModel= gson.fromJson(message, FacebookConfigModel.class);
		try {
			fbUserDataService.processUser(fbConfigModel);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
