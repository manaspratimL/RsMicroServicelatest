package com.rs.ms.in.service.impl;

import org.jinstagram.Instagram;
import org.springframework.stereotype.Service;

import com.rs.ms.in.service.base.InstagramClientService;

@Service
public class InstagramClientServiceImpl implements InstagramClientService{

	private Instagram instagram;

	public Instagram getInstagram() {
		return instagram;
	}

	public void setInstagram(Instagram instagram) {
		this.instagram = instagram;
	}
}
