package com.karsa.mq;

import com.karsa.entity.User;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

@Service
@RocketMQMessageListener(consumerGroup = "group1", topic = "topic19", selectorType = SelectorType.SQL92, selectorExpression = "age > 92", messageModel = MessageModel.BROADCASTING)
public class BroadcastingConsumer implements RocketMQListener<User> {
    @Override
    public void onMessage(User user) {
        System.out.println("==============");
        System.out.println(user);
        System.out.println("==============");
    }
}
