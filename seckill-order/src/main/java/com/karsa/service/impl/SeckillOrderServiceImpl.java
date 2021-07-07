package com.karsa.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.karsa.dto.GoodsInfo;
import com.karsa.dto.OrderDTO;
import com.karsa.entity.OrderInfo;
import com.karsa.entity.SeckillOrder;
import com.karsa.feign.GoodsClient;
import com.karsa.mapper.SeckillOrderMapper;
import com.karsa.service.IOrderInfoService;
import com.karsa.service.ISeckillOrderService;
import com.karsa.utils.RedisUtil;
import com.karsa.vo.prefix.OrderKeyPrefix;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author karsa
 * @since 2021-07-05
 */

@Slf4j
@Service
public class SeckillOrderServiceImpl extends ServiceImpl<SeckillOrderMapper, SeckillOrder> implements ISeckillOrderService {
    @Autowired
    private GoodsClient goodsClient;
    @Autowired
    private IOrderInfoService orderInfoService;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Object getInfo(String orderId) {
        SeckillOrder seckillOrder = this.getById(orderId);
        GoodsInfo info = goodsClient.getGoodsInfo(seckillOrder.getGoodsId());
        return new OrderDTO(seckillOrder, info);
    }

    /**
     * 减库存，生成订单，实现秒杀操作核心业务
     * 秒杀操作由两步构成，不可分割，为一个事务
     *
     * @param userId  秒杀商品的用户唯一ID号
     * @param goodsId 所秒杀的商品
     * @return
     */
    @Transactional
    @Override
    public OrderInfo seckill(long userId, long goodsId) {

        // 1. 减库存
        goodsClient.reduceStock(goodsId);
        // 2. 生成订单,向 order_info 表中订单信息
        OrderInfo order = orderInfoService.createOrder(userId, goodsId);
        // 3. 生成秒杀订单；向 seckill_order 表中写入信息
        this.createSeckillOrder(order);
        log.info("订单生成成功,用户ID {},商品ID {}", userId, goodsId);
        return order;
    }

    /**
     * 生成秒杀订单
     *
     * @param order
     */
    private void createSeckillOrder(OrderInfo order) {
        Long userId = order.getUserId();
        Long goodsId = order.getGoodsId();
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setGoodsId(goodsId);
        seckillOrder.setOrderId(order.getId());
        seckillOrder.setUserId(userId);
        this.save(seckillOrder);
        // 将秒杀订单概要信息存储于redis中
        redisUtil.set(OrderKeyPrefix.SK_ORDER + ":" + userId + "_" + goodsId, seckillOrder);
    }
}
