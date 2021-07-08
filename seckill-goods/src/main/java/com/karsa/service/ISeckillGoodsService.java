package com.karsa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.karsa.entity.SeckillGoods;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author karsa
 * @since 2021-07-04
 */
public interface ISeckillGoodsService extends IService<SeckillGoods> {
    /**
     * 激活秒杀商品(所有)
     */
    void activateAll();

    /**
     * 激活秒杀商品(单个)
     */
    void activateOne(Long id);
}
