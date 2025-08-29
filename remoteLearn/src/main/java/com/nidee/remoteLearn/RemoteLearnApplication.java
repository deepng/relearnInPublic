package com.nidee.remoteLearn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class RemoteLearnApplication {

	private static final Logger log = LoggerFactory.getLogger(RemoteLearnApplication.class);

	private static Environment env;

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(RemoteLearnApplication.class);
		env = app.run(args).getEnvironment();
		logApplictionStartup(env);
	}

	public static void logApplictionStartup(Environment env) {
		log.info("Application '{}' is running!", env.getProperty("spring.application.name"));
	}

}
