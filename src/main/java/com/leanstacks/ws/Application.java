package com.leanstacks.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Spring Boot main application class.
 * 
 * @author Matt Warman
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
@EnableAsync
public class Application {

    /**
     * Entry point for the application.
     * 
     * @param args Command line arguments.
     */
    public static void main(final String... args) {
        SpringApplication.run(Application.class, args);
    }

}
