package com.rs.ms.fb.service.impl;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.rs.ms.fb.service.base.FacebookClientService;
import org.springframework.stereotype.Service;

/**
 * 
 * @author ManasC
 *
 */
@Service
public class FacebookClientServiceImpl implements FacebookClientService {

	@Override
	public FacebookClient getFacebookClient(String accessToken, Version version) {
		
		return new DefaultFacebookClient(accessToken,Version.VERSION_2_6);
	}

}
