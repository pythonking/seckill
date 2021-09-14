package com.karsa.service;

import com.karsa.dto.ResultMap;

/**
 * @program: seckill
 * @description:
 * @author: Karsa
 * @since: 2021-09-14
 * @version: 1.0.0
 **/
public interface IWxPayService {
    /**
     * @Description: 微信支付统一下单
     * @param orderNo: 订单编号
     * @param amount: 实际支付金额
     * @param body: 订单描述
     * @Author:
     * @Date: 2021/9/14
     * @return
     */
    ResultMap unifiedOrder(String orderNo, double amount, String body) ;

    /**
     * @Description: 订单支付异步通知
     * @param notifyStr: 微信异步通知消息字符串
     * @Author:
     * @Date: 2021/9/14
     * @return
     */
    String notify(String notifyStr) throws Exception;

    /**
     * @Description: 退款
     * @param orderNo: 订单编号
     * @param amount: 实际支付金额
     * @param refundReason: 退款原因
     * @Author: XCK
     * @Date: 2019/8/6
     * @return
     */
    ResultMap refund(String orderNo, double amount, String refundReason);
}
