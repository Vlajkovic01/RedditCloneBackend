package com.example.RedditClone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableJpaRepositories("com.example.RedditClone.repository.jpa")
@EnableElasticsearchRepositories("com.example.RedditClone.repository.elasticsearch")
public class RedditCloneApplication {
	public static void main(String[] args) {
		SpringApplication.run(RedditCloneApplication.class, args);
	}

}
