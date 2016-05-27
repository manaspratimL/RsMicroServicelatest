package com.rs.ms.fb.service.base;

import com.restfb.types.webhook.WebhookObject;
import org.springframework.stereotype.Component;

/**
 * 
 * @author ManasC
 *
 */
@Component
public interface FacebookUserWebhookService {

	public void processFieldChanges(WebhookObject webhookObject);
}
