package com.karsa.service.impl;

import com.alibaba.excel.EasyExcel;
import com.karsa.dto.GoodsInfo;
import com.karsa.feign.GoodsClient;
import com.karsa.service.IWorkService;
import com.karsa.vo.excel.GoodsExcelVo;
import lombok.Cleanup;
import org.assertj.core.util.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.util.List;

@Service
public class WorkServiceImpl implements IWorkService {
    @Autowired
    private GoodsClient goodsClient;

    @Override
    public List<GoodsExcelVo> listGoodsExcel() {
        List<GoodsExcelVo> goodsExcleList = Lists.newArrayList();
        List<GoodsInfo> infoList = goodsClient.listAllInfo();
        if (!CollectionUtils.isEmpty(infoList)) {
            for (GoodsInfo goods : infoList) {
                GoodsExcelVo excelVo = new GoodsExcelVo();
                BeanUtils.copyProperties(goods, excelVo);
                goodsExcleList.add(excelVo);
            }
        }
        return goodsExcleList;
    }

    @Override
    public void downGoods() throws FileNotFoundException, IOException {
        List<GoodsExcelVo> excelVos = this.listGoodsExcel();
        @Cleanup OutputStream os = new FileOutputStream(new File("demo.xlsx"));
        EasyExcel.write(os, GoodsExcelVo.class)
                .sheet("商品信息表")
                .doWrite(excelVos);
    }
}
