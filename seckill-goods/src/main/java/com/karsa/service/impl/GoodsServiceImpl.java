package com.karsa.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.karsa.dto.GoodsInfo;
import com.karsa.dto.GoodsListReq;
import com.karsa.dto.GoodsVo;
import com.karsa.entity.Goods;
import com.karsa.mapper.GoodsMapper;
import com.karsa.service.IGoodsService;
import org.assertj.core.util.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {

    @Override
    public List<GoodsVo> listGoodsVo() {
        return this.baseMapper.listGoodsVo();
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

    @Override
    public List<GoodsInfo> listAllInfo() {
        List<GoodsInfo> infoList = Lists.emptyList();
        List<Goods> goodsList = this.list();
        if (CollectionUtils.isEmpty(goodsList)) {
            return infoList;
        }
        infoList = Lists.newArrayList();
        for (Goods goods : goodsList) {
            GoodsInfo goodsInfo = new GoodsInfo();
            BeanUtils.copyProperties(goods, goodsInfo);
            infoList.add(goodsInfo);
        }
        return infoList;
    }

    @Override
    @Transactional
    public Boolean batchInfoInsert(List<GoodsInfo> infoList) {
        List<Goods> goodsList = Lists.newArrayList();
        for (GoodsInfo goodsInfo : infoList) {
            Goods goods = new Goods();
            BeanUtils.copyProperties(goodsInfo, goods);
            goodsList.add(goods);
        }
        return this.saveBatch(goodsList);
    }

    @Override
    public List<Goods> listByReq(GoodsListReq req) {
        return this.list();
    }
}
