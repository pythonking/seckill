package com.karsa.controller;


import com.karsa.service.IJdContentService;
import com.karsa.utils.EsPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class JdContentController {
    @Autowired
    private IJdContentService jdContentService;

    @GetMapping("/parse/{keyword}")
    public Object parse(@PathVariable("keyword") String keyword) throws IOException {
        Boolean flag = jdContentService.butchBulk(keyword);
        return flag;
    }

    @GetMapping("/search/{index}/{keyword}/{pageNo}/{pageSize}/{highLightFlag}")
    public Object search(@PathVariable("index") String index, @PathVariable("keyword") String keyword, @PathVariable("pageNo") int pageNo, @PathVariable("pageSize") int pageSize, @PathVariable("highLightFlag") boolean highLightFlag) throws IOException {
        List<Map<String, Object>> maps = jdContentService.searchPage(new EsPage(index, keyword, pageNo, pageSize, highLightFlag));
        return maps;
    }
}

