package com.karsa.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: seckill
 * @author: Karsa
 * @since: 2021-09-14
 * @version: 1.0.0
 **/
public interface IAlipayViewService {
    /**
     * 订单支付
     *
     * @param orderId
     * @return
     */
    Object setGotoPayInfos(Long orderId);

    /**
     * 支付回调
     *
     * @param request
     * @param response
     * @return
     */
    String notify(HttpServletRequest request, HttpServletResponse response);
}
