package com.karsa.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @program: seckill
 * @description:
 * @author: Karsa
 * @since: 2021-09-14
 * @version: 1.0.0
 **/
@Data
public class TenPayVO {
    //商户订单号
    private String outTradeNo;
    //业务结果
    private String resultCode;
    //签名方式
    private String signType;
    //签名
    private String sign;
    //交易类型
    private String tradeType;
    //交易状态
    private String tradeState;
    //商户号
    private String mchId;
    //付款银行
    private String bankType;
    //支付金额
    private BigDecimal totalFee;
    //币种
    private String feeType;
    //微信支付订单号
    private String transactionId;
    //支付完成时间
    private String timeEnd;
}
