package com.rs.ms.in.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.rs.ms.common.models.MessageHandler;
import com.rs.ms.in.models.InstagramConfigModel;
import com.rs.ms.in.service.base.InstagramUserService;

@Service
public class InstagramMessageHandler implements MessageHandler  {


	private final InstagramUserService inUserDataService;
	
	private Gson gson;

	@Autowired
	public InstagramMessageHandler(InstagramUserService inUserDataService) {
		this.inUserDataService = inUserDataService;
		this.gson = new Gson();
	}

	@Override
	public void handleMessage(String message) {
		
		InstagramConfigModel inConfigModel= gson.fromJson(message, InstagramConfigModel.class);
		try {
			inUserDataService.processUser(inConfigModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
