package com.rs.ms.fb.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rs.ms.common.dataModels.RsStream;
import com.rs.ms.common.dataModels.RsStreams;
import com.rs.ms.common.kafka.KafkaProducerService;
import com.rs.ms.fb.models.Change;
import com.rs.ms.fb.models.FbWebhookEntry;
import com.rs.ms.fb.models.FbWebhookObject;
import com.rs.ms.fb.models.Value;
import com.rs.ms.fb.service.base.FacebookPageWebhookService;

@Service
public class FacebookPageWebhookServiceImpl implements FacebookPageWebhookService {


	@Autowired
	KafkaProducerService kafkaProducer;

	@Async
	@Override
	public void processFieldChanges(FbWebhookObject webhookObject) throws Exception {

		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		for (FbWebhookEntry entry : webhookObject.getEntry()) {

			RsStreams rsStreams = new RsStreams();
			rsStreams.setSocialMediaUUID(entry.getId());
			rsStreams.setProvider("FACEBOOK");

			for (Change change : entry.getChanges()) {
				RsStream stream = new RsStream();

				stream.getSourceInfo().setInteractionType("PUBLIC");
				stream.getSourceInfo().setName("FACEBOOK");

				Value value = change.getValue();

				stream.setEventType(value.getItem().toUpperCase());
				stream.setEventAction(value.getVerb().toUpperCase());

				if (value.getItem().equalsIgnoreCase("comment")) {
					stream.setSourceId(value.getComment_id());
				} else if (value.getItem().equalsIgnoreCase("status")) {
					stream.setSourceId(value.getPost_id());
				}

				stream.setParentSourceId(value.getParent_id());
				stream.getSource().setFullName(value.getSender_name());
				stream.getSource().setUserId(value.getSender_id());
				stream.setCreatedTime(value.getCreated_time());
				rsStreams.getRsStream().add(stream);

			}

			String json = gson.toJson(rsStreams);
			System.out.println(json);

			kafkaProducer.publishUpdates(json, "rsmsfb");

		}

	}

}
