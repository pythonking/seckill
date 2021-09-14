package com.karsa.controller;

import com.karsa.dto.ResultMap;
import com.karsa.service.IWxPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @program: seckill
 * @description: 微信支付接口
 * @author: Karsa
 * @since: 2021-09-14
 * @version: 1.0.0
 **/
@Slf4j
@RestController
@RequestMapping("/wxPay")
public class WxPayController {
    @Autowired
    private IWxPayService wxPayService;

    /**
     * 统一下单接口
     */
    @GetMapping("/unifiedOrder")
    public ResultMap unifiedOrder(String orderNo, double amount, String body, HttpServletRequest request) {
        try {
            // 1、验证订单是否存在

            // 2、开始微信支付统一下单
            ResultMap resultMap = wxPayService.unifiedOrder(orderNo, amount, body);
            return resultMap;//系统通用的返回结果集，见文章末尾
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResultMap.error("运行异常，请联系管理员");
        }
    }

    /**
     * 微信支付异步通知
     */
    @RequestMapping(value = "/notify")
    public String payNotify(HttpServletRequest request) {
        InputStream is = null;
        String xmlBack = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[报文为空]]></return_msg></xml> ";
        try {
            is = request.getInputStream();
            // 将InputStream转换成String
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            xmlBack = wxPayService.notify(sb.toString());
        } catch (Exception e) {
            log.error("微信手机支付回调通知失败：", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return xmlBack;
    }

    /**
     * 退款
     *
     * @param orderNo
     * @param amount
     * @param refundReason
     * @return
     */
    @PostMapping("/refund")
    public ResultMap refund(String orderNo, double amount, String refundReason) {
        return wxPayService.refund(orderNo, amount, refundReason);
    }

}
