package com.karsa.controller;


import com.karsa.service.IWorkService;
import com.karsa.vo.result.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 工具类
 */
@RestController
@RequestMapping("/work")
public class WorkController {

    @Autowired
    private IWorkService workService;

    @GetMapping("/goods/down")
    public Object downGoods() throws IOException {
        workService.downGoods();
        return Results.success();
    }

    @GetMapping("/goods/read")
    public Object readGoods() throws IOException {
        Boolean flag = workService.readExcel();
        return Results.success(flag);
    }

}

