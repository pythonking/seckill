package com.karsa.controller;

import com.karsa.service.IAlipayViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 工具类
 */
@RestController
@RequestMapping("/pay")
public class AliPayController {

    @Autowired
    private IAlipayViewService alipayViewService;

    /**
     * 支付订单
     *
     * @param orderId
     * @return
     */
    @PostMapping("/gotopay")
    public Object goToPay(Long orderId) {
        return alipayViewService.setGotoPayInfos(orderId);
    }

    /**
     * 支付宝异步通知回调
     */
    @ResponseBody
    @RequestMapping("/notify")
    public String notify(HttpServletRequest request, HttpServletResponse response) {
        return alipayViewService.notify(request, response);
    }
}

