package com.karsa;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.karsa.mapper")
public class SeckillGoodsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeckillGoodsApplication.class, args);
    }

}
