package com.karsa.service;

import com.karsa.utils.EsPage;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IJdContentService {
    /**
     * 根据关键字爬虫相关数据放入es
     *
     * @param keyword
     * @return
     * @throws IOException
     */
    Boolean butchBulk(String keyword) throws IOException;

    /**
     * 从es查询数据
     *
     * @param page
     * @return
     * @throws IOException
     */
    List<Map<String, Object>> searchPage(EsPage page) throws IOException;
}
