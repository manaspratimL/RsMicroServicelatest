package com.rs.ms.in.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.jinstagram.Instagram;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rs.ms.in.service.base.InstagramWebhookService;
import com.rs.ms.in.subscription.InstagramSubscriptionService;
import com.rs.ms.in.subscription.SubscriptionResponse;


@RestController
public class InstagramWebhookController {
	
	@Autowired
	InstagramWebhookService inWebhookService;

	@RequestMapping(value ="/instagram", method = RequestMethod.GET)
	public String getinstagramChallenge(@RequestParam(value = "hub.mode") String mode,
			@RequestParam(value = "hub.verify_token") String verify_token, @RequestParam(value = "hub.challenge") String challenge) {
		
		if(mode.equalsIgnoreCase("subscribe") && verify_token.equalsIgnoreCase("token")){
			return challenge;
		}
		
		return "Challenge Failed";
	}

	@RequestMapping(value = "/instagram", method = RequestMethod.POST)
	public void instagramDataStream(HttpServletRequest request,String data) throws Exception {
				
		String pushedJsonAsString = IOUtils.toString(request.getInputStream(),"utf-8");
		System.out.println("JSON String : "+pushedJsonAsString);
		
		//old code
		//inWebhookService.processChange(pushedJsonAsString);
		
		inWebhookService.processNewPost(pushedJsonAsString);
		
	}
	
	@RequestMapping(value = "/createSubs", method = RequestMethod.GET)
	public SubscriptionResponse createSubs() throws IOException {
		
		InstagramSubscriptionService igSub = new InstagramSubscriptionService()
                .clientId("")
                .clientSecret("")
                .object("user")
                .aspect("media")
                .callback("http://yourcallbackurl/handleSubscription")
                .verifyToken("token");
		
		SubscriptionResponse response=igSub.createSubscription();
		
		return response;
		
	}
}
