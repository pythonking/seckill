package com.karsa.dto;

import com.karsa.entity.SeckillOrder;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class OrderDTO implements Serializable {
    private SeckillOrder seckillOrder;
    private GoodsInfo goodsInfo;
}
