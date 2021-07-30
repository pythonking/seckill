package com.karsa.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Mapping;

@Data
@Accessors(chain = true)
@Document(indexName = "somehi_index")
@Mapping(mappingPath = "mapping/tangshiinfo_mapping.json")
public class TangshiInfo {
    @Id
    private Integer id;
    @Field(type = FieldType.Keyword)
    private String author;//作者
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String type;//类型
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title;//标题
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String contents;//内容
}
