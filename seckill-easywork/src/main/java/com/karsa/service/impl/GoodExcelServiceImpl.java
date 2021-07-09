package com.karsa.service.impl;

import com.karsa.dto.GoodsInfo;
import com.karsa.feign.GoodsClient;
import com.karsa.service.IGoodExcelService;
import com.karsa.vo.excel.GoodsExcelVo;
import org.assertj.core.util.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class GoodExcelServiceImpl implements IGoodExcelService {
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
    public Boolean batchExcelInsert(List list) {
        if (CollectionUtils.isEmpty(list)) {
            return Boolean.FALSE;
        }
        List<GoodsInfo> infoList = Lists.newArrayList();
        for (Object excelVO : list) {
            GoodsInfo goodsInfo = new GoodsInfo();
            BeanUtils.copyProperties(excelVO, goodsInfo);
            infoList.add(goodsInfo);
        }
        return goodsClient.batchInfoInsert(infoList);
    }
}
