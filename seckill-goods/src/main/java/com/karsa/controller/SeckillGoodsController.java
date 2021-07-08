package com.karsa.controller;


import com.karsa.service.ISeckillGoodsService;
import com.karsa.vo.result.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author karsa
 * @since 2021-07-04
 */
@RestController
@RequestMapping("/seckill-goods")
public class SeckillGoodsController {
    @Autowired
    private ISeckillGoodsService seckillGoodsService;

    @PostMapping("/activate")
    public Object activateAll() {
        seckillGoodsService.activateAll();
        return Results.success(true);
    }

    @PostMapping("/activate")
    public Object activateOne(Long id) {
        seckillGoodsService.activateOne(id);
        return Results.success(true);
    }
}

