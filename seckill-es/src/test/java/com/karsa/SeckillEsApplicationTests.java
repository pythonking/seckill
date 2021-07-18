package com.karsa;

import com.alibaba.fastjson.JSON;
import com.karsa.dto.GoodsInfo;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
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


    @Test
    void testCreateIndex() throws IOException {
        //1.创建索引请求
        CreateIndexRequest request = new CreateIndexRequest("karsa_index2");
        //2.执行请求
        CreateIndexResponse response = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        log.info("获取创建索引 {}", response.index());
    }


    /**
     * 更新文档信息
     *
     * @throws IOException
     */
    @Test
    void queryDocument() throws IOException {

        GetRequest request = new GetRequest("phone_index", "1");

        GetResponse documentFields = restHighLevelClient.get(request, RequestOptions.DEFAULT);
        log.info("文档内容 {}", documentFields.getSource());  // 获取文档内容 返回 Map
        log.info("文档所有内容 {}", documentFields);  // 获取文档所有信息


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
        GoodsInfo info = new GoodsInfo("小米手机", new BigDecimal(1999), 1000);

        updateRequest.doc(JSON.toJSONString(info), XContentType.JSON);

        UpdateResponse update = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);

        System.out.println(update.status());

        System.out.println(update);


    }


    /**
     * 删除文档
     *
     * @throws IOException
     */
    @Test
    void deleteDocument() throws IOException {

        DeleteRequest deleteRequest = new DeleteRequest("phone_index", "1");
        deleteRequest.timeout("1s");

        DeleteResponse delete = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println(delete.status());

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

        arrayList.add(new GoodsInfo("小米手机", new BigDecimal(1999), 1000));
        arrayList.add(new GoodsInfo("苹果手机", new BigDecimal(5999), 3000));
        arrayList.add(new GoodsInfo("华为手机", new BigDecimal(4999), 2222));
        arrayList.add(new GoodsInfo("OPPO手机", new BigDecimal(3999), 4444));
        arrayList.add(new GoodsInfo("VIVO手机", new BigDecimal(2999), 5555));
        arrayList.add(new GoodsInfo("三星手机", new BigDecimal(999), 6666));


        for (int i = 0; i < arrayList.size(); i++) {
            bulkRequest.add(new IndexRequest("phone_index").id("" + (i + 1)).source(JSON.toJSONString(arrayList.get(i)), XContentType.JSON));
        }

        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);

        //是否失败 false 就是成功
        log.info("获取结果 {} ", bulk.hasFailures());

    }


    /**
     * 查询数据
     *
     * @throws IOException
     */
    @Test
    void queryData() throws IOException {

        SearchRequest searchRequest = new SearchRequest("changan_index");

        // 构建搜索条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        /*
         * 查询条件, 我们可以使用 QueryBuilders 工具类来实现
         * QueryBuilders.termQuery()  精确查询
         * QueryBuilders.matchAllQuery() 查询所有数据
         */
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "hjj");

        searchSourceBuilder.query(termQueryBuilder);

        // 设置搜索时长
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        searchRequest.source(searchSourceBuilder);

        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        System.out.println(JSON.toJSONString(search.getHits()));

        System.out.println("===========");


        for (SearchHit searchHit : search.getHits().getHits()) {

            System.out.println(searchHit.getSourceAsMap());
        }

    }
}
