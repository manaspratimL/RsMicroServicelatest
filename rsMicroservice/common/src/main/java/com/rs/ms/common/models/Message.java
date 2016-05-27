package com.rs.ms.common.models;

import com.rs.ms.common.models.MessageType.MediaType;

public interface Message {

	MediaType type();
	String message();
}
