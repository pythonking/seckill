package com.karsa.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Data
@Document("movies")
public class Movie implements Serializable {
    @Id
    private String id;
    /**
     * 名称
     */
    @Field("name")
    private String name;
    /**
     * 发布年
     */
    @Field("years")
    private Integer years;
    /**
     * 导演性别
     */
    private Boolean gender;
    /**
     * 发时间
     */
    private String birth;
    /**
     * 导演
     */
    private String director;
}
