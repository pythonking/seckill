package com.karsa.controller;


import com.karsa.mq.send.MqProviderApi;
import com.karsa.vo.mq.SkMessage;
import com.karsa.vo.result.Results;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author karsa
 * @since 2021-07-07
 */
@RestController
@RequestMapping("/order-info")
public class OrderInfoController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/mq/send")
    public Object getResult(Long orderId) {
        SkMessage skMessage = new SkMessage(123999, orderId);
        rabbitTemplate.convertAndSend("helloSK", skMessage);
        return Results.success(orderId);
    }
}

