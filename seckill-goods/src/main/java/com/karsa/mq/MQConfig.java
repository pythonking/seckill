package com.karsa.mq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * rabbitmq配置类
 * @author java1234_小锋
 * @site www.java1234.com
 * @company Java知识分享网
 * @create 2021-02-27 20:58
 */
@Configuration
public class MQConfig {

    public static final String HELLO_SK="helloSK";

    @Bean
    public Queue queue(){
        return new Queue(HELLO_SK);
    }
}
