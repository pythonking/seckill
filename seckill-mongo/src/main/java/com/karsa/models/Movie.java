package com.karsa.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Data
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
    private int gender;
    /**
     * 发时间
     */
    private String birth;
    /**
     * 导演
     */
    private String director;
}
