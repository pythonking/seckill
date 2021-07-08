package com.karsa.mq.receive;

import com.karsa.entity.SeckillOrder;
import com.karsa.service.ISeckillOrderService;
import com.karsa.utils.RedisUtil;
import com.karsa.vo.mq.SkMessage;
import com.karsa.vo.prefix.OrderKeyPrefix;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * MQ消息接收者, 消费者
 * 消费者绑定在队列监听，既可以接收到队列中的消息
 *
 * @author mata
 */
@Slf4j
@Service
public class MqConsumer {


    @Autowired
    ISeckillOrderService seckillService;

    @Autowired
    RedisUtil redisUtil;

    /**
     * 处理收到的秒杀成功信息（核心业务实现）
     *
     * @param message
     */
    @RabbitListener(queues = MQConfig.SECKILL_QUEUE)
    @RabbitHandler
    public void receiveSkInfo(SkMessage message, Channel channel, Message mes) throws IOException {
        log.info("MQ receive a message: " + message);
        // 1.减库存 2.写入订单 3.写入秒杀订单
        // 获取秒杀用户信息与商品id
        Long userId = message.getUserID();
        long goodsId = message.getGoodsId();
        try {
            seckillService.seckill(userId, goodsId);
            channel.basicAck(mes.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            if (redisUtil.get(OrderKeyPrefix.SK_ORDER + ":" + userId + "_" + goodsId, SeckillOrder.class) != null) {
                log.debug("消息已重复处理,拒绝再次接收...");
                channel.basicReject(mes.getMessageProperties().getDeliveryTag(), false); // 拒绝消息
            } else {
                if (mes.getMessageProperties().getRedelivered())
                    channel.basicReject(mes.getMessageProperties().getDeliveryTag(), false);
                else {
                    log.error("消息即将再次返回队列处理...");
                    channel.basicNack(mes.getMessageProperties().getDeliveryTag(), false, true);
                }
            }
        }

    }
}

