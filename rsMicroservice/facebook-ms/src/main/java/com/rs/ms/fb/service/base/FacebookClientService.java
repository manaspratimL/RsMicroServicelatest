package com.rs.ms.fb.service.base;

import org.springframework.stereotype.Component;

import com.restfb.FacebookClient;
import com.restfb.Version;

/**
 * 
 * @author ManasC
 *
 */
@Component
public interface FacebookClientService {
	
	public FacebookClient getFacebookClient(String token, Version version);
}
