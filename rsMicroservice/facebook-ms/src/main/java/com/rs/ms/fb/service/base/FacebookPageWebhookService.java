package com.rs.ms.fb.service.base;

import com.rs.ms.fb.models.FbWebhookObject;

public interface FacebookPageWebhookService {
	
	public void processFieldChanges(FbWebhookObject webhookObject) throws Exception;
}
