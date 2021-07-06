package com.karsa.controller;


import com.karsa.service.ISeckillGoodsService;
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
 * @since 2021-07-04
 */
@RestController
@RequestMapping("/seckill-goods")
public class SeckillGoodsController {
    @Autowired
    private ISeckillGoodsService seckillService;

    @GetMapping("/seckill")
    public Object seckill(Long id) {
        return seckillService.seckill(id);
    }
}

