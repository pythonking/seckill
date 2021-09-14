package com.karsa.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Accessors(chain = true)
@Document(indexName = "somehi_index")
public class TangshiInfo {
    @Id
    private Integer id;
    /**
     * 作者
     */
    @Field(type = FieldType.Keyword)
    private String author;
    /**
     * 类型
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String type;
    /**
     * 标题
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title;
    /**
     * 内容
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String contents;
}