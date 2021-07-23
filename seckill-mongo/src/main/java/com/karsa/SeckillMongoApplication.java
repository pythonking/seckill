package com.karsa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
public class SeckillMongoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeckillMongoApplication.class, args);
    }

}
