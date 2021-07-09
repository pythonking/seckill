package com.karsa.endpoint;

import com.karsa.dto.GoodsInfo;
import com.karsa.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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


    @GetMapping(value = "/reduceStock")
    public int reduceStock(Long goodsId) {
        return goodsService.reduceStock(goodsId);
    }

}
