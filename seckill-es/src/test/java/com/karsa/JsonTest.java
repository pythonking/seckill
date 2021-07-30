package com.karsa;

import com.alibaba.fastjson.JSON;
import com.karsa.dto.TangshiInfo;
import com.karsa.utils.TangshiJsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class JsonTest {
    public static final String JSON_TANGSHI = "D:/workspace/seckill/seckill-common/schema/json/300.json";

    @Autowired
    RestHighLevelClient restHighLevelClient;

    /**
     * 特殊的批量插入数据
     *
     * @throws IOException
     */
    @Test
    public void bulkTangshi() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("1s");
        List<TangshiInfo> arrayList = JSON.parseArray(TangshiJsonUtil.readJsonFile(JSON_TANGSHI), TangshiInfo.class);
        for (TangshiInfo info : arrayList) {
            IndexRequest somehi_index = new IndexRequest("somehi_index").id("" + info.getId()).source(JSON.toJSONString(info), XContentType.JSON);
            bulkRequest.add(somehi_index);
        }
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        //是否失败 false 就是成功
        log.info("获取结果 {} ", bulk.hasFailures());
    }

    /**
     * 特殊的批量插入数据
     *
     * @throws IOException
     */
    @Test
    public void createIndex() throws IOException {
        //1.创建索引请求
        CreateIndexRequest request = new CreateIndexRequest("somehi_index");
        request.mapping("{\n" +
                "  \"properties\": {\n" +
                "    \"author\": {\n" +
                "      \"type\": \"text\",\n" +
                "      \"fields\": {\n" +
                "        \"field\": {\n" +
                "          \"type\": \"keyword\"\n" +
                "        }\n" +
                "      },\n" +
                "      \"analyzer\": \"ik_max_word\"\n" +
                "    },\n" +
                "    \"contents\": {\n" +
                "      \"type\": \"text\",\n" +
                "      \"fields\": {\n" +
                "        \"field\": {\n" +
                "          \"type\": \"keyword\"\n" +
                "        }\n" +
                "      },\n" +
                "      \"analyzer\": \"ik_max_word\",\n" +
                "      \"fielddata\": true\n" +
                "    },\n" +
                "    \"timestamp\": {\n" +
                "      \"type\": \"date\"\n" +
                "    },\n" +
                "    \"title\": {\n" +
                "      \"type\": \"text\",\n" +
                "      \"fields\": {\n" +
                "        \"field\": {\n" +
                "          \"type\": \"keyword\"\n" +
                "        }\n" +
                "      },\n" +
                "      \"analyzer\": \"ik_max_word\"\n" +
                "    },\n" +
                "    \"type\": {\n" +
                "      \"type\": \"text\",\n" +
                "      \"fields\": {\n" +
                "        \"field\": {\n" +
                "          \"type\": \"keyword\"\n" +
                "        }\n" +
                "      },\n" +
                "      \"analyzer\": \"ik_max_word\"\n" +
                "    }\n" +
                "  }\n" +
                "}", XContentType.JSON);
        //2.执行请求
        CreateIndexResponse response = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        log.info("获取创建索引 {}", response.index());
    }
}
