package com.karsa.service;

import com.karsa.entity.SeckillOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author karsa
 * @since 2021-07-05
 */
public interface ISeckillOrderService extends IService<SeckillOrder> {

    Object getInfo(String orderId);
}
