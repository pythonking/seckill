package com.karsa.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document("movies")
public class Movie implements Serializable {
    @Id
    private String id;
    /**
     * 名称
     */
    private String name;
    /**
     * 发布年
     */
    private int years;
    /**
     * 导演性别
     */
    private boolean gender;
    /**
     * 发时间
     */
    private String birth;
    /**
     * 导演
     */
    private String director;
}
