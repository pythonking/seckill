package com.karsa.mq.send;


import com.karsa.vo.mq.SkMessage;

/**
 * 消息队列服务
 *
 * @author noodle
 */
public interface MqProviderApi {

    /**
     * 将用户秒杀信息投递到MQ中（使用direct模式的exchange）
     *
     * @param message
     */
    void sendSkMessage(SkMessage message);
}
