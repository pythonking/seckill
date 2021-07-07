package com.karsa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.karsa.entity.OrderInfo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author karsa
 * @since 2021-07-07
 */
public interface IOrderInfoService extends IService<OrderInfo> {
    /**
     * 创建订单
     *
     * @param userId
     * @param goodsId
     * @return
     */
    OrderInfo createOrder(Long userId, Long goodsId);
}
