package com.karsa.utils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class EsUtils {
    public static <T> BulkRequest buildBulkRequest(String esIndex, List<T> list) {
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("1s");
        for (int i = 0; i < list.size(); i++) {
            bulkRequest.add(new IndexRequest(esIndex).source(JSON.toJSONString(list.get(i)), XContentType.JSON));
        }
        return bulkRequest;
    }

    /**
     * 查询数据
     * SearchRequest 搜索请求
     * SearchSourceBuilder 条件构造
     * QueryBuilders 条件构造
     * HighlightBuilder 高亮构造
     * TermQueryBuilder 精确构造
     * MatchQueryBuilder 匹配构造
     */
    public static SearchRequest buildSearchRequest(EsPage page) {
        //条件搜索
        SearchRequest searchRequest = new SearchRequest(page.getIndex());
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //分页
        sourceBuilder.from(page.getPageNo());
        sourceBuilder.size(page.getPageSize());
        //精准匹配
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("title", page.getKeyword());
        sourceBuilder.query(termQueryBuilder);
        //设置超时
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(sourceBuilder);
        return searchRequest;
    }


    public static List<Map<String, Object>> parseResponse(SearchResponse response) {
        if (null == response) {
            return null;
        }
        List<Map<String, Object>> list = Lists.newArrayList();
        for (SearchHit documentFields : response.getHits().getHits()) {
            list.add(documentFields.getSourceAsMap());
        }
        return list;
    }


}
