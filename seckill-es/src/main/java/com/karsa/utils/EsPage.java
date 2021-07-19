package com.karsa.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EsPage {
    /**
     * 索引
     */
    private String index = "jd_goods";
    /**
     * 关键字
     */
    private String keyword = "go";
    /**
     * 页码
     */
    private int pageNo = 1;
    /**
     * 每页数量
     */
    private int pageSize = 10;
}
