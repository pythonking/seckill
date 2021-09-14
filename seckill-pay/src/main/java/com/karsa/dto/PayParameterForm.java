package com.karsa.dto;

import lombok.Data;

/**
 * @program: seckill
 * @description:
 * @author: Karsa
 * @since: 2021-09-14
 * @version: 1.0.0
 **/
@Data
public class PayParameterForm {
    /**
     * 主题
     */
    private String title;
    /**
     * 订单ID
     */
    private Long orderId;
}
