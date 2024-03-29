package com.karsa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SeckillGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeckillGatewayApplication.class, args);
    }

}
