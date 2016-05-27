package com.rs.ms;


import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


/**
 * 
 * @author ManasC
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = { "com.rs.ms"})
@EnableAsync
@EnableWebMvc
public class RSMicroServiceApplication {
	
	
    public static void main(String[] args) throws IOException {
        SpringApplication.run(RSMicroServiceApplication.class, args);
        //KafkaConsumerService.consumeUpdates();
    }
    

}