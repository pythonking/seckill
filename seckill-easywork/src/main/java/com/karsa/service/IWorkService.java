package com.karsa.service;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface IWorkService {

    void writeGoods() throws IOException;

    void writeGoods2() throws IOException;

    /**
     * 导入excel
     *
     * @throws FileNotFoundException
     */
    Boolean readExcel() throws IOException;
}
