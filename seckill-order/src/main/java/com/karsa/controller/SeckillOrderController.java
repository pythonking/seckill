package com.karsa.controller;


import com.karsa.service.ISeckillOrderService;
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
 * @since 2021-07-05
 */
@RestController
@RequestMapping("/order")
public class SeckillOrderController {
    @Autowired
    private ISeckillOrderService seckillOrderService;

    @GetMapping("/list")
    public Object list(String orderId) {
        return seckillOrderService.getInfo(orderId);
    }

    @GetMapping("/info")
    public Object getInfo(String orderId) {
        return seckillOrderService.getInfo(orderId);
    }

    @GetMapping("/seckill/result")
    public Object getResult(Long orderId) {
        Long userId = null;
        return seckillOrderService.getSeckillResult(userId, orderId);
    }
}

