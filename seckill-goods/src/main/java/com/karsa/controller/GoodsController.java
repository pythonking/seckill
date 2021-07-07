package com.karsa.controller;


import com.karsa.entity.Goods;
import com.karsa.service.IGoodsService;
import com.karsa.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author karsa
 * @since 2021-07-04
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/list")
    public Object listGoods() {
        return goodsService.list();
    }

    @GetMapping("/info")
    public Object getInfo() {
        Goods goods = new Goods();
        goods.setGoodsImg("img2").setGoodsName("小米13").setGoodsPrice(new BigDecimal("12.5"));
        redisUtil.set("goods1", goods);
        Goods goods2 = redisUtil.get("goods1", Goods.class);
        return "获取名称： " + goods2;
    }
}

