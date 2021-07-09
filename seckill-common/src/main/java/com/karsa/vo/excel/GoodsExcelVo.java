package com.karsa.vo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class GoodsExcelVo implements Serializable {
    /**
     * 商品ID
     */
    @ExcelProperty("商品ID")
    private Long id;

    /**
     * 商品名称
     */
    @ExcelProperty("商品名称")
    private String goodsName;

    /**
     * 商品标题
     */
    @ExcelProperty("商品标题")
    private String goodsTitle;

    /**
     * 商品图片
     */
    @ExcelProperty("商品图片")
    private String goodsImg;

    /**
     * 商品的详情介绍
     */
    @ExcelProperty("详情介绍")
    private String goodsDetail;

    /**
     * 商品单价
     */
    @ExcelProperty("商品单价")
    private BigDecimal goodsPrice;

    /**
     * 商品库存，-1表示没有限制
     */
    @ExcelProperty("商品库存")
    private Integer goodsStock;
}
