package com.karsa.service;

import com.karsa.vo.excel.GoodsExcelVo;

import java.util.List;

public interface IGoodExcelService {
    List<GoodsExcelVo> listGoodsExcel();

    /**
     * 批量插入excel数据
     *
     * @param list
     */
    Boolean batchExcelInsert(List list);
}
