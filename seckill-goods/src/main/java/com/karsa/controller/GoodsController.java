package com.karsa.controller;


import com.karsa.dto.GoodsListReq;
import com.karsa.entity.Goods;
import com.karsa.service.IGoodsService;
import com.karsa.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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
    public Object listGoods(@RequestBody @Valid GoodsListReq req) {
        return goodsService.listByReq(req);
    }

    @GetMapping("/info")
    public Object getInfo(String key) {
        Goods goods = new Goods();
        goods.setGoodsImg("img2").setGoodsName("小米14").setGoodsPrice(new BigDecimal("12.53"));
        redisUtil.set("goods14", goods);
        redisUtil.set(key, key);
        Goods goods2 = redisUtil.get("goods14", Goods.class);
        return "获取名称： " + goods2;
    }
}

