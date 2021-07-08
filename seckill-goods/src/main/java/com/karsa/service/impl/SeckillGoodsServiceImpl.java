package com.karsa.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.karsa.dto.GoodsVo;
import com.karsa.entity.SeckillGoods;
import com.karsa.mapper.SeckillGoodsMapper;
import com.karsa.service.IGoodsService;
import com.karsa.service.ISeckillGoodsService;
import com.karsa.utils.RedisUtil;
import com.karsa.vo.prefix.GoodsKeyPrefix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author karsa
 * @since 2021-07-04
 */
@Service
public class SeckillGoodsServiceImpl extends ServiceImpl<SeckillGoodsMapper, SeckillGoods> implements ISeckillGoodsService {
    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private RedisUtil redisUtil;


    @Override
    public void activateAll() {
        List<GoodsVo> goods = goodsService.listGoodsVo();
        if (CollectionUtils.isEmpty(goods)) {
            return;
        }
        // 将商品的库存信息存储在redis中
        for (GoodsVo good : goods) {
            redisUtil.set(GoodsKeyPrefix.GOODS_STOCK.getPrefix() + good.getId(), good.getStockCount());
            redisUtil.set(GoodsKeyPrefix.seckillGoodsInf.getPrefix() + good.getId(), good);
        }
    }

    @Override
    public void activateOne(Long id) {
        GoodsVo good = goodsService.getGoodsVoByGoodsId(id);
        if (null == good) {
            return;
        }
        redisUtil.set(GoodsKeyPrefix.GOODS_STOCK.getPrefix() + good.getId(), good.getStockCount());
        redisUtil.set(GoodsKeyPrefix.seckillGoodsInf.getPrefix() + good.getId(), good);
    }
}
