package com.karsa.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.karsa.dto.GoodsInfo;
import com.karsa.dto.GoodsVo;
import com.karsa.entity.Goods;
import com.karsa.mapper.GoodsMapper;
import com.karsa.service.IGoodsService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

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
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {

    @Override
    public List<GoodsVo> listGoodsVo() {
        return this.baseMapper.listGoodsVo();
    }

    @Override
    public GoodsVo getGoodsVoByGoodsId(long goodsId) {
        return this.baseMapper.getGoodsVoByGoodsId(goodsId);
    }

    @Override
    public GoodsVo getGoodsVoByGoodsId(Long goodsId) {
        return this.baseMapper.getGoodsVoByGoodsId(goodsId);
    }

    @Override
    public int reduceStock(long goodsId) {
        return this.baseMapper.reduceStack(goodsId);
    }

    @Override
    public GoodsInfo getGoodsInfo(Long goodsId) {
        Goods goods = this.getById(goodsId);
        GoodsInfo goodsInfo = null;
        if (null != goods) {
            goodsInfo = new GoodsInfo();
            BeanUtils.copyProperties(goods, goodsInfo);
        }
        return goodsInfo;
    }
}
