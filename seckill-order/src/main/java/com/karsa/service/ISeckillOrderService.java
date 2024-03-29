package com.karsa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.karsa.entity.OrderInfo;
import com.karsa.entity.SeckillOrder;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author karsa
 * @since 2021-07-05
 */
public interface ISeckillOrderService extends IService<SeckillOrder> {

    Object getInfo(String orderId);

    /**
     * 执行秒杀操作
     *
     * @param userId
     * @param goodsId
     * @return
     */
    OrderInfo seckill(long userId, long goodsId);

    /**
     * 获取秒杀结果
     *
     * @param userId
     * @param goodsId
     * @return
     */
    long getSeckillResult(Long userId, long goodsId);

    /**
     * 获取秒杀结果对象
     *
     * @param userId
     * @param goodsId
     * @return
     */
    SeckillOrder getByUserIdAndGoodsId(Long userId, long goodsId);
}
