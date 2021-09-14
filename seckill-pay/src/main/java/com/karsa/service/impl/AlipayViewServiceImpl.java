package com.karsa.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.karsa.config.AlipayConfig;
import com.karsa.dto.PayParameterForm;
import com.karsa.service.IAlipayViewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @program: seckill
 * @description:
 * @author: hp
 * @since: 2021-09-14
 * @version: 1.0.0
 **/
@Service
@Slf4j
public class AlipayViewServiceImpl implements IAlipayViewService {
    @Override
    public Object setGotoPayInfos(Long orderId) {
        Map<String, Object> map = new HashMap<>();
        if (null == orderId) {
            map.put("code", "400");
            map.put("msg", "参数为空");
            return map;
        }
        /* 查询订单信息 */
        PayParameterForm payParameter = new PayParameterForm();//实际开发中从mapper中获取订单信息
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.url, AlipayConfig.app_id, AlipayConfig.private_key, AlipayConfig.format, AlipayConfig.charset, AlipayConfig.public_key, AlipayConfig.signtype);//支付宝需要的参数serverUrl、appId、private_key、format、charset、public_key、signType
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody(payParameter.getTitle());//商品信息
        model.setSubject(payParameter.getTitle());//商品名称
        model.setOutTradeNo(String.valueOf(payParameter.getOrderId()));//订单号
        model.setTimeoutExpress("30m");//支付超时时间
        model.setTotalAmount(String.valueOf(0.01));// 支付金额
        request.setBizModel(model);
        // 回调地址(充值订单)
        request.setNotifyUrl(AlipayConfig.notify_url);// 回调地址
        //这里和普通的接口调用不同，使用的是sdkExecute
        AlipayTradeAppPayResponse response = null;
        try {
            response = alipayClient.sdkExecute(request);
            map.put("code", "200");
            map.put("msg", "SUCCESS");
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("payPath", response.getBody());
            map.put("data", dataMap);
            return map;
        } catch (AlipayApiException e) {
            log.error("支付异常,{}", e);
        }
        map.put("code", "500");
        map.put("msg", "");
        return map;
    }


    /**
     * @Function: 支付宝异步回调接口
     * @author: YangXueFeng
     * @Date: 2019/6/11 20:03
     */
    @Override
    public String notify(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> params = new HashMap<String, String>();
        //从支付宝回调的request域中取值
        Map<String, String[]> requestParams = request.getParameterMap();

        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = iter.next();
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }
        //商品订单号
        String out_trade_no = request.getParameter("out_trade_no");   // 商户订单号
        // 当前交易状态
        String tradeStatus = request.getParameter("trade_status");   //交易状态
        // 支付金额
        String totalAmount = request.getParameter("total_amount");   //交易状态
        // 支付时间
        String payDate = request.getParameter("gmt_payment");   //交易状态
        //3.签名验证(对支付宝返回的数据验证，确定是支付宝返回的)
        boolean signVerified = false;
        try {
            //3.1调用SDK验证签名
            signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.public_key, AlipayConfig.charset, AlipayConfig.signtype);
        } catch (AlipayApiException e) {
            log.error("支付回调异常,{}", e);
        }
        //返回状态存入redis中
        //对验签进行处理
        if (signVerified) {
            //验签通过
            if (tradeStatus.equals("TRADE_SUCCESS")) {
                //支付成功后的业务处理
                //OrderEntity order = orderMapper.getOrderInfo(Long.valueOf(out_trade_no));
                //if (null == order) {
                //    order.setStatus(CalculatStaticConstant.CHECK_ONE);
                //    order.setCompleteTime(new Date());
                //    orderMapper.updateOrder(order);
                //}
                //OrderPayEntity orderPay = new OrderPayEntity();
                //orderPay.setId(Long.valueOf(IdUtils.getPrimaryKey()));
                //orderPay.setOrderId(order.getId());
                //orderPay.setUserId(order.getUserId());
                //orderPay.setPayPrice(Double.valueOf(totalAmount));
                //orderPay.setPayType(PayTypeEnum.ALI_PAY.intKey());
                //orderPay.setStatus(CalculatStaticConstant.CHECK_ONE);
                //orderPay.setPayTime(payDate);
                //orderMapper.saveOrderPay(orderPay);
                //RedisUtil.set("ali" + out_trade_no, tradeStatus, 300);
                return "success";
            }
        } else { //验签不通过
            System.err.println("验签失败");
            return "failure";
        }
        return "failure";
    }
}
