package com.karsa.service.impl;

import com.karsa.entity.SeckillGoods;
import com.karsa.mapper.SeckillGoodsMapper;
import com.karsa.service.ISeckillGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author karsa
 * @since 2021-07-04
 */
@Service
public class SeckillGoodsServiceImpl extends ServiceImpl<SeckillGoodsMapper, SeckillGoods> implements ISeckillGoodsService {

    @Override
    public Boolean seckill(Long id) {
        //秒杀操作分为三步
        //1.创建秒杀订单并放到MQ
        return null;
    }
}
