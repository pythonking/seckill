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
    public Object getInfo(String orderId) {
        return seckillOrderService.getInfo(orderId);
    }
}

