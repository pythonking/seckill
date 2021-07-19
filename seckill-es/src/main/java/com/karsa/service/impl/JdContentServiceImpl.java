package com.karsa.service.impl;

import com.alibaba.fastjson.JSON;
import com.karsa.dto.JdContent;
import com.karsa.service.IJdContentService;
import com.karsa.utils.EsPage;
import com.karsa.utils.EsUtils;
import com.karsa.utils.HtmlParseUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class JdContentServiceImpl implements IJdContentService {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    public Boolean butchBulk(String keyword) throws IOException {
        List<JdContent> jdContentList = HtmlParseUtil.parseJD(keyword);
        BulkRequest bulkRequest = EsUtils.buildBulkRequest("jd_goods", jdContentList);
        BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        return !bulkResponse.hasFailures();
    }

    @Override
    public List<Map<String, Object>> searchPage(EsPage esPage) throws IOException {
        SearchRequest searchRequest = EsUtils.buildSearchRequest(esPage);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        log.info("结果集 {}", JSON.toJSONString(searchResponse.getHits()));
        return EsUtils.parseResponse(searchResponse);
    }
}
