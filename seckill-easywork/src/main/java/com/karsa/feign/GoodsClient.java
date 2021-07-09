package com.karsa.feign;


import com.karsa.dto.GoodsInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "seckill-goods")
public interface GoodsClient {

    /**
     * 获取所有商品
     *
     * @param
     * @return
     */
    @GetMapping(value = "/endpoint/listAllInfo")
    List<GoodsInfo> listAllInfo();

    /**
     * 批量插入
     *
     * @param infoList
     */
    @PostMapping(value = "/endpoint/batchInfoInsert")
    Boolean batchInfoInsert(@RequestBody List<GoodsInfo> infoList);
}
