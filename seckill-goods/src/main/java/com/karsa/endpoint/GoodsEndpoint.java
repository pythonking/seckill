package com.karsa.endpoint;

import com.karsa.dto.GoodsInfo;
import com.karsa.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/endpoint")
public class GoodsEndpoint {

    @Autowired
    IGoodsService goodsService;


    @GetMapping(value = "/getGoodsInfo")
    public GoodsInfo getGoodsInfo(Long goodsId) {
        return goodsService.getGoodsInfo(goodsId);
    }

    /**
     * 获取所有商品信息
     *
     * @return
     */
    @GetMapping(value = "/listAllInfo")
    public List<GoodsInfo> listAllInfo() {
        return goodsService.listAllInfo();
    }

    /**
     * 扣减库存
     *
     * @param goodsId
     * @return
     */
    @GetMapping(value = "/reduceStock")
    public int reduceStock(Long goodsId) {
        return goodsService.reduceStock(goodsId);
    }

    /**
     * 批量插入
     *
     * @param infoList
     * @return
     */
    @PostMapping(value = "/batchInfoInsert")
    public Boolean batchInfoInsert(List<GoodsInfo> infoList) {
        return goodsService.batchInfoInsert(infoList);
    }

}
