package com.karsa.utils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class EsUtils {
    /**
     * 列表数据放入 es
     *
     * @param esIndex
     * @param list
     * @param <T>
     * @return
     */
    public static <T> BulkRequest buildBulkRequest(String esIndex, List<T> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("1s");
        for (int i = 0; i < list.size(); i++) {
            bulkRequest.add(new IndexRequest(esIndex).source(JSON.toJSONString(list.get(i)), XContentType.JSON));
        }
        return bulkRequest;
    }

    /**
     * 构建查询数据请求
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
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        //分页
        sourceBuilder.from(page.getPageNo());
        sourceBuilder.size(page.getPageSize());
        //精准匹配
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("title", page.getKeyword());
        sourceBuilder.query(termQueryBuilder);
        buildHighLightRequest(page.isHighLightFlag(), sourceBuilder);
        //搜索条件放入查询条件
        searchRequest.source(sourceBuilder);
        return searchRequest;
    }


    /**
     * 解析搜索结果:放入map文件
     *
     * @param response
     * @return
     */
    public static List<Map<String, Object>> parseResponse(boolean highLightFlag, SearchResponse response) {
        if (null == response) {
            return null;
        }
        if (highLightFlag) {
            return parseHighlightResponse(response);
        }
        List<Map<String, Object>> list = Lists.newArrayList();
        for (SearchHit documentFields : response.getHits().getHits()) {
            list.add(documentFields.getSourceAsMap());
        }
        return list;
    }

    /**
     * 构建高亮搜索
     *
     * @param highLightFlag
     * @param sourceBuilder
     */
    private static void buildHighLightRequest(boolean highLightFlag, SearchSourceBuilder sourceBuilder) {
        if (!highLightFlag) {
            return;
        }
        //高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("title");
        highlightBuilder.requireFieldMatch(false);//多个高亮显示
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");
        sourceBuilder.highlighter(highlightBuilder);
    }

    /**
     * 解析搜索结果:高亮,放入map文件
     *
     * @param response
     * @return
     */
    private static List<Map<String, Object>> parseHighlightResponse(SearchResponse response) {
        List<Map<String, Object>> list = Lists.newArrayList();
        for (SearchHit documentFields : response.getHits().getHits()) {
            Map<String, HighlightField> highlightFields = documentFields.getHighlightFields();
            HighlightField title = highlightFields.get("title");
            Map<String, Object> sourceAsMap = documentFields.getSourceAsMap();
            if (null != title) {
                Text[] fragments = title.fragments();
                String n_title = "";
                for (Text text : fragments) {
                    n_title += text;
                }
                sourceAsMap.put("title", n_title);
            }
            list.add(sourceAsMap);
        }
        return list;
    }


}
