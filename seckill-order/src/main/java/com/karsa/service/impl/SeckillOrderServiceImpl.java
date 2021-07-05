package com.karsa.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.karsa.dto.GoodsInfo;
import com.karsa.dto.OrderDTO;
import com.karsa.entity.SeckillOrder;
import com.karsa.feign.GoodsClient;
import com.karsa.mapper.SeckillOrderMapper;
import com.karsa.service.ISeckillOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author karsa
 * @since 2021-07-05
 */
@Service
public class SeckillOrderServiceImpl extends ServiceImpl<SeckillOrderMapper, SeckillOrder> implements ISeckillOrderService {
    @Autowired
    private GoodsClient goodsClient;

    @Override
    public Object getInfo(String orderId) {
        SeckillOrder seckillOrder = this.getById(orderId);
        GoodsInfo info = goodsClient.getGoodsInfo(seckillOrder.getGoodsId());
        return new OrderDTO(seckillOrder,info);
    }
}
