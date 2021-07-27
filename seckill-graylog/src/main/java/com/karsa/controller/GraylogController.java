package com.karsa.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/seckill-graylog")
public class GraylogController {

    @GetMapping("/query")
    public Object queryLog() {
        log.info("INFO查询学生信息");
        log.warn("WARN查询学生信息");
        log.error("ERROR查询学生信息");
        return "success";
    }

}

