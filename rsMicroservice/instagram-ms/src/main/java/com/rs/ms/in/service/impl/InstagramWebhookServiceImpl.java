package com.rs.ms.in.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.jinstagram.Instagram;
import org.jinstagram.entity.media.MediaInfoFeed;
import org.jinstagram.entity.users.feed.MediaFeedData;
import org.jinstagram.exceptions.InstagramException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rs.ms.common.dataModels.RsStream;
import com.rs.ms.common.dataModels.RsStreams;
import com.rs.ms.common.kafka.KafkaProducerService;
import com.rs.ms.in.models.Change;
import com.rs.ms.in.models.InWebhookEntry;
import com.rs.ms.in.models.InWebhookObject;
import com.rs.ms.in.models.InstagramData;
import com.rs.ms.in.models.StreamData;
import com.rs.ms.in.models.Value;
import com.rs.ms.in.service.base.InstagramClientService;
import com.rs.ms.in.service.base.InstagramUserService;
import com.rs.ms.in.service.base.InstagramWebhookService;

/**
 * 
 * @author ManasC
 *
 */
@Service
public class InstagramWebhookServiceImpl implements InstagramWebhookService {

	@Autowired
	KafkaProducerService kafkaProducer;

	@Autowired
	InstagramUserService inUserService;

	@Autowired
	InstagramClientService inClientService;


	public void processChange(String jsonArray) throws Exception {
		
		Gson gson = new GsonBuilder().setDateFormat("yyyyMMdd").setPrettyPrinting().create();

		StreamData[] streamDataArray = gson.fromJson(jsonArray, StreamData[].class);

		InWebhookObject webObject = new InWebhookObject();

		webObject.setObject("user");

		InWebhookEntry entry = new InWebhookEntry();

		for (StreamData stream : streamDataArray) {
			Change change = createChangeObj(stream);
			entry.getChanges().add(change);
			entry.setId(stream.getObject_id());
			entry.setTime(stream.getTime().toString());
		}

		webObject.getEntryList().add(entry);

		InstagramData data = new InstagramData();

		data.setInWebhookObject(webObject);
		data.setType("STREAM");

		String json = gson.toJson(data);
		System.out.println(json);
		// produce to kafka
		kafkaProducer.publishUpdates(json, "rsmsin");
	}

	private Change createChangeObj(StreamData stream) throws InstagramException {
		Change change = new Change();
		change.setField(stream.getChanged_aspect());

		Value value = new Value();

		value.setPost_id(stream.getData().getMedia_id());
		value.setSender_id(stream.getObject_id());
		value.setSender_name(this.userToken.get(stream.getObject_id()));

		// get the access token from the userToken Map and the below commented
		// method
		// MediaInfoFeed
		// media=inUserService.getMedia(inClientService.getInstagram(),value.getPost_id());

		// Temp flow above code will be used
		Instagram in = new Instagram("2993976879.26aac15.a3d332c80e6a4a6b8c12079f384393da",
				"0cfc3d0b91054025b4329b7257032417");
		MediaInfoFeed media = inUserService.getMedia(in, value.getPost_id());

		value.setMediaUrl(media.getData().getImages().getStandardResolution().getImageUrl());
		value.setMessage(media.getData().getCaption().getText());
		change.setValue(value);

		return change;
	}

	public void processNewPost(String jsonArray) throws Exception {
		Gson gson = new GsonBuilder().setDateFormat("yyyyMMdd").setPrettyPrinting().create();

		StreamData[] streamDataArray = gson.fromJson(jsonArray, StreamData[].class);

		for (StreamData data : streamDataArray) {

			RsStreams rsStreams = new RsStreams();

			rsStreams.setProvider("INSTAGRAM");
			rsStreams.setSocialMediaUUID(data.getObject_id());

			RsStream stream = createRsStreamObj(data);
			rsStreams.getRsStream().add(stream);

			String json = gson.toJson(stream);
			System.out.println(json);
			// produce to kafka
			kafkaProducer.publishUpdates(json, "rsmsin");
		}

	}

	private RsStream createRsStreamObj(StreamData data) throws InstagramException {

		RsStream stream = new RsStream();

		stream.getSourceInfo().setInteractionType("PUBLIC");
		stream.getSourceInfo().setName("FACEBOOK");

		stream.setEventType(data.getChanged_aspect());
		stream.setEventAction("ADD");

		stream.setSourceId(data.getData().getMedia_id());
		stream.setCreatedTime(data.getTime());

		// get the access token from the userToken Map and the below commented
		// method
		// MediaInfoFeed
		// media=inUserService.getMedia(inClientService.getInstagram(),stream.getSourceId());

		// Temp flow.. above code will be used
		Instagram in = new Instagram("2993976879.26aac15.a3d332c80e6a4a6b8c12079f384393da",
				"0cfc3d0b91054025b4329b7257032417");
		MediaInfoFeed mediaInfo = inUserService.getMedia(in, stream.getSourceId());

		MediaFeedData media = mediaInfo.getData();

		stream.setContent(media.getCaption().getText());

		if (media.getType().equalsIgnoreCase("image")) {
			stream.getMediaEntity().setMediaUrl(media.getImages().getStandardResolution().getImageUrl());
		} else {
			stream.getMediaEntity().setMediaUrl(media.getVideos().getStandardResolution().getUrl());
		}

		return stream;
	}

}
