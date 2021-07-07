package com.karsa.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.karsa.dto.GoodsVo;
import com.karsa.entity.OrderInfo;
import com.karsa.mapper.OrderInfoMapper;
import com.karsa.service.IOrderInfoService;
import com.karsa.service.ISeckillOrderService;
import com.karsa.utils.RedisUtil;
import com.karsa.vo.GoodsKeyPrefix;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author karsa
 * @since 2021-07-07
 */
@Slf4j
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements IOrderInfoService {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    ISeckillOrderService seckillOrderService;

    /**
     * 创建订单
     * <p>
     * 首先向数据库中写入数据，然后将数据写到缓存中，这样可以保证缓存和数据库中的数据的一致
     * 1. 向 order_info 中插入订单详细信息
     * 2. 向 seckill_order 中插入订单概要
     * 两个操作需要构成一个数据库事务
     *
     * @param userId
     * @param goodsId
     * @return
     */
    @Transactional
    @Override
    public OrderInfo createOrder(Long userId, Long goodsId) {
        OrderInfo orderInfo = new OrderInfo();
        GoodsVo goods = (GoodsVo) redisUtil.get(GoodsKeyPrefix.seckillGoodsInf.getPrefix(), goodsId.toString());

        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);// 订单中商品的数量
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setGoodsPrice(goods.getSeckillPrice());// 秒杀价格
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId(userId);

        // 将订单信息插入 order_info 表中
        long orderId = this.baseMapper.insert(orderInfo);
        log.debug("将订单信息插入 order_info 表中: 记录为" + orderId);
        return orderInfo;
    }
}
