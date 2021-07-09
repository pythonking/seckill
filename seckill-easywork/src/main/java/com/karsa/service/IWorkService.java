package com.karsa.service;

import java.io.FileNotFoundException;

public interface IWorkService {

    void downGoods() throws FileNotFoundException;

    /**
     * 导入excel
     *
     * @throws FileNotFoundException
     */
    Boolean readExcel() throws FileNotFoundException;
}
