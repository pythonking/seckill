package com.karsa;

import com.alibaba.fastjson.JSON;
import com.karsa.dto.GoodsInfo;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
class SeckillEsApplicationTests {
    @Autowired
    RestHighLevelClient restHighLevelClient;

    //创建索引
    @Test
    void testCreateIndex() throws IOException {
        //1.创建索引请求
        CreateIndexRequest request = new CreateIndexRequest("karsa_index2");
        //2.执行请求
        CreateIndexResponse response = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        log.info("获取创建索引 {}", response.index());
    }

    //检测索引
    @Test
    void testIfExistIndex() throws IOException {
        //1.创建索引请求
        GetIndexRequest request = new GetIndexRequest("karsa_index3");
        //2.执行请求
        boolean exists = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
        log.info("检测索引是否存在 {}", exists);
    }

    //检测索引
    @Test
    void testDelIndex() throws IOException {
        //1.创建索引请求
        DeleteIndexRequest request = new DeleteIndexRequest("karsa_index2");
        //2.执行请求
        AcknowledgedResponse delete = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
        log.info("删除索引 {}", delete.isAcknowledged());
    }


    /**
     * 查询文档信息
     *
     * @throws IOException
     */
    @Test
    void queryDocument() throws IOException {

        GetRequest request = new GetRequest("karsa_index", "V8QvuXoBhMB5gN_AWKgN");
        //不获取 Source 返回的上下文
//        request.fetchSourceContext(new FetchSourceContext(false));
//        request.storedFields("_none_");

        boolean exists = restHighLevelClient.exists(request, RequestOptions.DEFAULT);
        log.info("文档内容是否存在 {}", exists);

        GetResponse documentFields = restHighLevelClient.get(request, RequestOptions.DEFAULT);
        log.info("文档内容 {}", documentFields.getSourceAsString());  // 获取文档内容 返回 Map
        log.info("文档所有内容 {}", documentFields);  // 获取文档所有信息


    }


    /**
     * 添加文档信息
     *
     * @throws IOException
     */
    @Test
    void addDocument() throws IOException {
        IndexRequest request = new IndexRequest("karsa_index");
        request.timeout("1s");

        // 创建新对象
        GoodsInfo info = new GoodsInfo("小米手机", new BigDecimal(1999), 1000);
        //将数据放入请求
        request.source(JSON.toJSONString(info), XContentType.JSON);
        //客户端发送请求
        IndexResponse indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        log.info("获取创建对象,{}", indexResponse);
        log.info("获取创建对象状态,{}", indexResponse.status());

    }


    /**
     * 更新文档信息
     *
     * @throws IOException
     */
    @Test
    void updateDocument() throws IOException {

        UpdateRequest updateRequest = new UpdateRequest("phone_index", "1");
        updateRequest.timeout("1s");

        // 创建新对象
        GoodsInfo info = new GoodsInfo("小米手机", new BigDecimal(2999.9), 1001);

        updateRequest.doc(JSON.toJSONString(info), XContentType.JSON);

        UpdateResponse update = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);

        log.info("获取状态,{}", update.status());
        log.info("获取结果,{}", update);


    }


    /**
     * 删除文档
     *
     * @throws IOException
     */
    @Test
    void deleteDocument() throws IOException {

        DeleteRequest deleteRequest = new DeleteRequest("phone_index", "6");
        deleteRequest.timeout("1s");

        DeleteResponse delete = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        log.info("获取结果,{},状态,{}", delete, delete.status());

    }

    /**
     * 特殊的批量插入数据
     *
     * @throws IOException
     */
    @Test
    void bulkInsert() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("1s");

        List<GoodsInfo> arrayList = new ArrayList<>();

        arrayList.add(new GoodsInfo("mobile1", "小米手机", new BigDecimal(1999), 1000));
        arrayList.add(new GoodsInfo("mobile2", "苹果手机", new BigDecimal(5999), 3000));
        arrayList.add(new GoodsInfo("mobile3", "华为手机", new BigDecimal(4999), 2222));
        arrayList.add(new GoodsInfo("mobile4", "OPPO手机", new BigDecimal(3999), 4444));
        arrayList.add(new GoodsInfo("mobile5", "VIVO手机", new BigDecimal(2999), 5555));
        arrayList.add(new GoodsInfo("mobile6", "三星手机", new BigDecimal(999), 6666));


        for (int i = 0; i < arrayList.size(); i++) {
            bulkRequest.add(new IndexRequest("phone_index").id("" + (i + 1)).source(JSON.toJSONString(arrayList.get(i)), XContentType.JSON));
        }

        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);

        //是否失败 false 就是成功
        log.info("获取结果 {} ", bulk.hasFailures());

    }


    /**
     * 查询数据
     * SearchRequest 搜索请求
     * SearchSourceBuilder 条件构造
     * QueryBuilders 条件构造
     * HighlightBuilder 高亮构造
     * TermQueryBuilder 精确构造
     * MatchQueryBuilder 匹配构造
     *
     * @throws IOException
     */
    @Test
    void queryData() throws IOException {

        SearchRequest searchRequest = new SearchRequest("phone_index");

        // 构建搜索条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        searchSourceBuilder.highlighter();

        /*
         * 查询条件, 我们可以使用 QueryBuilders 工具类来实现
         * QueryBuilders.termQuery()  精确查询
         * QueryBuilders.matchAllQuery() 查询所有数据
         */
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "mobile1");
//        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
//        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("name", "mobile");

        searchSourceBuilder.query(termQueryBuilder);

        // 设置搜索时长
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        searchRequest.source(searchSourceBuilder);

        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        log.info("结果集 {}", JSON.toJSONString(search.getHits()));

        System.out.println("===========");


        for (SearchHit searchHit : search.getHits().getHits()) {

            log.info("获取结果 {}", searchHit.getSourceAsMap());
        }

    }
}
