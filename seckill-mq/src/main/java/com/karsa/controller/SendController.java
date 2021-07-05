package com.karsa.controller;


import com.google.common.collect.Lists;
import com.karsa.entity.User;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/")
public class SendController {
    @Autowired
    RocketMQTemplate rocketMQTemplate;


    @GetMapping("/send")
    public String send(String msg) {
        User user = new User().setUsercode("wqw").setPhone("1860000").setEmail(msg);
        rocketMQTemplate.convertAndSend("topic10", user);
        return "success:" + msg;
    }

    @GetMapping("/sync")
    public String sync(String msg) {
        User user = new User().setUsercode("wqw").setPhone("1860000").setEmail(msg);
        SendResult result = rocketMQTemplate.syncSend("topic10", user);
        return "success:" + msg + ":" + result;
    }

    @GetMapping("/async")
    public String async(String msg) {
        User user = new User().setUsercode("wqw").setPhone("1860000").setEmail(msg);
        rocketMQTemplate.asyncSend("topic10", user, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("发送成功");
            }

            @Override
            public void onException(Throwable throwable) {
                System.out.println("发送失败");
            }
        }, 1000);
        return "success";
    }

    @GetMapping("/one")
    public String sendOneWay(String msg) {
        User user = new User().setUsercode("wqw").setPhone("1860000").setEmail(msg);
        rocketMQTemplate.sendOneWay("topic10", user);
        return "success";
    }

    @GetMapping("/delay")
    public String delay(String msg) {
        User user = new User().setUsercode("wqw").setPhone("1860000").setEmail(msg);
        rocketMQTemplate.syncSend("topic10", MessageBuilder.withPayload(msg).build(), 1000, 2);
        return "success";
    }

    @GetMapping("/batch")
    public String batch() {
        String msg1 = "msg1";
        String msg2 = "msg2";
        String msg3 = "msg3";
        String msg4 = "msg4";
        Message m1 = new Message("", "", msg1.getBytes());
        Message m2 = new Message("", "", msg2.getBytes());
        Message m3 = new Message("", "", msg3.getBytes());
        Message m4 = new Message("", "", msg4.getBytes());
        ArrayList<Message> msgList = Lists.newArrayList();
        msgList.add(m1);
        msgList.add(m2);
        msgList.add(m3);
        msgList.add(m4);
        rocketMQTemplate.syncSend("topic10", msgList, 1000);
        return "success";
    }
}

