package com.karsa.controller;


import com.karsa.entity.SeckillOrder;
import com.karsa.mq.send.MqProviderApi;
import com.karsa.service.ISeckillOrderService;
import com.karsa.utils.RedisUtil;
import com.karsa.vo.mq.SkMessage;
import com.karsa.vo.prefix.GoodsKeyPrefix;
import com.karsa.vo.prefix.OrderKeyPrefix;
import com.karsa.vo.result.CodeMsg;
import com.karsa.vo.result.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/order")
public class SeckillOrderController {
    @Autowired
    private ISeckillOrderService seckillOrderService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    MqProviderApi sender;

    @GetMapping("/list")
    public Object list(String orderId) {
        return seckillOrderService.getInfo(orderId);
    }

    @GetMapping("/info")
    public Object getInfo(String orderId) {
        return seckillOrderService.getInfo(orderId);
    }

    /* 压力测试时候注释掉 :验证码和秒杀地址隐藏 */

    /**
     * 秒杀逻辑（页面静态化分离，不需要直接将页面返回给客户端，而是返回客户端需要的页面动态数据，返回数据时json格式）
     * GET/POST的@RequestMapping是有区别的
     * 通过随机的path，客户端隐藏秒杀接口
     * 优化: 不同于每次都去数据库中读取秒杀订单信息，而是在第一次生成秒杀订单成功后，
     * 将订单存储在redis中，再次读取订单信息的时候就直接从redis中读取
     *
     * @param goodsId
     * @return 订单详情或错误码
     */
    @PostMapping(value = "/doSeckill")
    public Results<Integer> doSeckill(long goodsId) {


        // 预减库存，同时在库存为0时标记该商品已经结束秒杀
        Long stock = redisUtil.decr(GoodsKeyPrefix.GOODS_STOCK.getPrefix() + goodsId);
        if (stock < 0) {
            redisUtil.set(GoodsKeyPrefix.GOODS_STOCK.getPrefix() + goodsId, 0);
            return Results.error(CodeMsg.SECKILL_OVER);
        }
        Long userId = new Random().nextLong();
        // 判断是否重复秒杀
        // 从redis中取缓存，减少数据库的访问
        SeckillOrder order = redisUtil.get(OrderKeyPrefix.SK_ORDER.getPrefix() + userId + "_" + goodsId, SeckillOrder.class);
        // 如果缓存中不存该数据，则从数据库中取
        if (order == null) {
            order = seckillOrderService.getByUserIdAndGoodsId(userId, goodsId);
        }
        if (order != null) {
            return Results.error(CodeMsg.REPEATE_SECKILL);
        }
        // 商品有库存且用户为秒杀商品，则将秒杀请求放入MQ
        SkMessage message = new SkMessage(userId, goodsId);
        // 放入MQ(对秒杀请求异步处理，直接返回)
        sender.sendSkMessage(message);
        // 排队中
        return Results.success(0);
    }

    @GetMapping("/seckill/result")
    public Object getResult(Long orderId) {
        Long userId = null;
        return seckillOrderService.getSeckillResult(userId, orderId);
    }
}

