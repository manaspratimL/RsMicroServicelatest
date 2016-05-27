package com.rs.ms.kafka;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;
import java.util.Random;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.google.common.io.Resources;
import com.rs.ms.common.models.Consumer;
import com.rs.ms.common.models.DefaultMessage;
import com.rs.ms.common.models.Message;
import com.rs.ms.common.models.MessageType;

@Service
public class EventConsumer extends Thread implements ApplicationListener<ContextRefreshedEvent>, Consumer {

	private final MessageType messageType;

	public void run() {
		System.out.println("thread is running...");
		try {
			consume();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Autowired
	public EventConsumer(MessageType messageType) {
		this.messageType = messageType;
	}

	@Override
	public void consume() throws IOException {
		// and the consumer
		KafkaConsumer<String, String> consumer;
		try (InputStream props = Resources.getResource("consumer.props").openStream()) {
			Properties properties = new Properties();
			properties.load(props);
			if (properties.getProperty("group.id") == null) {
				properties.setProperty("group.id", "group-" + new Random().nextInt(100000));
			}
			consumer = new KafkaConsumer<>(properties);
		}
		consumer.subscribe(Arrays.asList("config"));
		int timeouts = 0;
		// noinspection InfiniteLoopStatement

		while (true) {
			// read records with a short timeout
			ConsumerRecords<String, String> records = consumer.poll(200);
			if (records.count() == 0) {
				timeouts++;
			} else {
				timeouts = 0;
			}
			for (ConsumerRecord<String, String> record : records) {
				switch (record.topic()) {
				case "config":
					StringBuilder msg = new StringBuilder(record.value());
					msg = msg.deleteCharAt(0);
					msg = msg.deleteCharAt(msg.length() - 1);
					String actualMsg = msg.toString().replaceAll("\"", "'").replaceAll("\\\\", "").replaceFirst("'message':'","'message':\"");
					
					int ind = actualMsg.lastIndexOf("'}");
					if( ind>=0 )
						actualMsg = new StringBuilder(actualMsg).replace(ind, ind+1,"\"}").toString();
					
					try {
						Message message = DefaultMessage.parse(actualMsg);
						messageType.handler(message.type()).handleMessage(message.message());
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				default:
					throw new IllegalStateException("Shouldn't be possible to get message on topic " + record.topic());
				}
			}
		}
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		this.start();
	}

}
