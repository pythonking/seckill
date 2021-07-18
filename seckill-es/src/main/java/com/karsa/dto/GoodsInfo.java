package com.karsa.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 *
 * </p>
 *
 * @author karsa
 * @since 2021-07-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@NoArgsConstructor
public class GoodsInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品ID
     */
    private Long id;

    /**
     * 商名称
     */
    private String name;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品标题
     */
    private String goodsTitle;

    /**
     * 商品的图片
     */
    private String goodsImg;

    /**
     * 商品的详情介绍
     */
    private String goodsDetail;

    /**
     * 商品单价
     */
    private BigDecimal goodsPrice;

    /**
     * 商品库存，-1表示没有限制
     */
    private Integer goodsStock;

    public GoodsInfo(String goodsName, BigDecimal goodsPrice, Integer goodsStock) {
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
        this.goodsStock = goodsStock;
    }

    public GoodsInfo(String name, String goodsName, BigDecimal goodsPrice, Integer goodsStock) {
        this.name = name;
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
        this.goodsStock = goodsStock;
    }
}
