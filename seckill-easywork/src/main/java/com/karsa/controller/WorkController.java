package com.karsa.controller;


import com.karsa.dto.GoodsInfo;
import com.karsa.feign.GoodsClient;
import com.karsa.vo.result.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 工具类
 */
@RestController
@RequestMapping("/work")
public class WorkController {

    @Autowired
    private GoodsClient goodsClient;

    @GetMapping("/goods/list")
    public Object listGoods() {
        List<GoodsInfo> infoList = goodsClient.listAllInfo();
        return Results.success(infoList);
    }

}

