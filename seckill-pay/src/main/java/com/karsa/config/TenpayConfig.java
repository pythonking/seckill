package com.karsa.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @program: seckill
 * @description: 腾讯支付配置
 * @author: Karsa
 * @since: 2021-09-14
 * @version: 1.0.0
 **/
@Data
@Component
@ConfigurationProperties(prefix = "tenpayconfig")
public class TenpayConfig {
    //appId
    private String appId;
    //商户号
    private String mchId;
    //商户的key(API密匙)
    private String key;
    //API支付请求地址
    private String payUrl;
    //API查询请求地址
    private String queryUrl;
    //Sign=WXPay
    private String packageValue;
}
