package com.karsa.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * MQ消息接收者, 消费者
 * 消费者绑定在队列监听，既可以接收到队列中的消息
 *
 * @author mata
 */
@Slf4j
@Service
public class MqConsumer {

    @RabbitListener(queues = MQConfig.HELLO_MQ)
    public void receiveMq(String message) {
        log.info("收到消息" + message);

    }
}

