package com.karsa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.karsa.dto.GoodsInfo;
import com.karsa.dto.GoodsVo;
import com.karsa.entity.Goods;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author karsa
 * @since 2021-07-04
 */
public interface IGoodsService extends IService<Goods> {
    /**
     * 获取商品列表
     *
     * @return
     */
    List<GoodsVo> listGoodsVo();

    /**
     * 通过商品的id查出商品的所有信息（包含该商品的秒杀信息）
     *
     * @param goodsId
     * @return
     */
    GoodsVo getGoodsVoByGoodsId(Long goodsId);

    /**
     * order表减库存
     *
     * @param goodsId
     */
    int reduceStock(long goodsId);

    GoodsInfo getGoodsInfo(Long goodsId);

    /**
     * 获取所有商品
     *
     * @return
     */
    List<GoodsInfo> listAllInfo();
}
