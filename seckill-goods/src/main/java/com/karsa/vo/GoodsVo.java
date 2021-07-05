package com.karsa.vo;


import com.karsa.entity.Goods;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品信息（并且包含商品的秒杀信息）
 * 商品信息和商品的秒杀信息是存储在两个表中的（goods 和 seckill_goods）
 * 继承 Goods 便具有了 goods 表的信息，再额外添加上 seckill_goods 的信息即可
 *
 * @author noodle
 */
@Data
public class GoodsVo extends Goods implements Serializable {

    /*只包含了部分 seckill_goods 表的信息*/
    private Double seckillPrice;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
}
