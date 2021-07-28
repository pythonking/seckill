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
        for (int i = 0; i < 6; i++) {
            log.info("info:{}","info 昨天打印" + i);
            log.warn("warn:{}","warn 明天打印" + i);
            log.error("error:{}","error 后天打印" + i);
        }
        return "success";
    }

}

