package com.karsa.feign;


import com.karsa.dto.GoodsInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "seckill-goods")
public interface GoodsClient {
    /**
     * 获取商品详情
     *
     * @param goodsId
     * @return
     */
    @GetMapping(value = "/endpoint/getGoodsInfo")
    GoodsInfo getGoodsInfo(@RequestParam("goodsId") Long goodsId);

    /**
     * 减库存
     *
     * @param goodsId
     * @return
     */
    @GetMapping(value = "/endpoint/reduceStock")
    int reduceStock(long goodsId);
}
