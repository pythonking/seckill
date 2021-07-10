package com.karsa.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.event.SyncReadListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.karsa.service.IGoodExcelService;
import com.karsa.service.IWorkService;
import com.karsa.vo.excel.GoodsExcelVo;
import lombok.Cleanup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@Service
public class WorkServiceImpl implements IWorkService {

    private static final String PATH_EXCEL = "D:\\workspace\\excel\\";
    @Autowired
    private IGoodExcelService goodExcelService;


    @Override
    public void writeGoods() throws IOException {
        List<GoodsExcelVo> excelVos = goodExcelService.listGoodsExcel();
        @Cleanup OutputStream os = new FileOutputStream(new File(PATH_EXCEL + "商品表.xlsx"));
        EasyExcel.write(os, GoodsExcelVo.class).sheet("商品1").doWrite(excelVos);
    }


    @Override
    public void writeGoods2() throws IOException {
        List<GoodsExcelVo> excelVos = goodExcelService.listGoodsExcel();
        String fileName = PATH_EXCEL + "write" + File.separator + "商品表.xlsx";
        EasyExcel.write(fileName, GoodsExcelVo.class).sheet("商品1").doWrite(excelVos);
    }

    @Override
    public Boolean readExcel() throws IOException {

        @Cleanup InputStream inputStream = new FileInputStream(new File(PATH_EXCEL + "商品表.xlsx"));
        // Excel表格数据记录解析为具体的实体对象集合
        final SyncReadListener syncReadListener = new SyncReadListener();
        // Excel表格数据记录解析为具体的实体对象集合
        EasyExcel.read(inputStream, GoodsExcelVo.class, syncReadListener).build().read(new ReadSheet(0));
        List list = syncReadListener.getList();
        return goodExcelService.batchExcelInsert(list);
    }
}
