package com.karsa.feign;


import com.karsa.dto.GoodsInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

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
    @GetMapping(value = "/endpoint/batchInfoInsert")
    Boolean batchInfoInsert(List<GoodsInfo> infoList);
}
