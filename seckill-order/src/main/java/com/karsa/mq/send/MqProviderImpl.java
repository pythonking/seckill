package com.karsa.mq.send;


import com.karsa.mq.receive.MQConfig;
import com.karsa.vo.mq.SkMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


/**
 * 消息队列服务化（消息生产者）
 *
 * @author noodle
 */

@Service
public class MqProviderImpl implements MqProviderApi, RabbitTemplate.ConfirmCallback {

    private static Logger logger = LoggerFactory.getLogger(MqProviderImpl.class);

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public MqProviderImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        // 设置 ack 回调
        rabbitTemplate.setConfirmCallback(this);
    }

    @Override
    public void sendSkMessage(SkMessage message) {
        logger.info("MQ send message: " + message);
        // 秒杀消息关联的数据
        CorrelationData skCorrData = new CorrelationData(UUID.randomUUID().toString());
        // 第一个参数为消息队列名(此处也为routingKey)，第二个参数为发送的消息
        rabbitTemplate.convertAndSend(MQConfig.SECKILL_QUEUE, message, skCorrData);
    }

    /**
     * MQ ack 机制
     * TODO 完善验证机制，确保消息能够被消费，且不影响消息吞吐量
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        logger.info("SkMessage UUID: " + correlationData.getId());
        if (ack) {
            logger.info("SkMessage 消息消费成功！");
        } else {
            System.out.println("SkMessage 消息消费失败！");
            logger.info("CallBackConfirm Cause: " + cause);
        }

    }
}
