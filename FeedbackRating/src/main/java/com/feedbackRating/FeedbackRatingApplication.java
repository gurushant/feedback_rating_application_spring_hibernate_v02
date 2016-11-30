package com.feedbackRating;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class FeedbackRatingApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(FeedbackRatingApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		// TODO Auto-generated method stub
		return application.sources(FeedbackRatingApplication.class);
	}
}
