package com.karsa.service;

import com.karsa.vo.excel.GoodsExcelVo;

import java.io.IOException;
import java.util.List;

public interface IWorkService {
    List<GoodsExcelVo> listGoodsExcel();

    void downGoods() throws IOException;
}
