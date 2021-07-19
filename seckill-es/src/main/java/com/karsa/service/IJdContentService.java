package com.karsa.service;

import com.karsa.utils.EsPage;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IJdContentService {
    Boolean butchBulk(String keyword) throws IOException;

    List<Map<String, Object>> searchPage(EsPage page) throws IOException;
}
