package com.karsa.enums;


import com.karsa.utils.Result;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ResponseEnum {
    code_10000("参数异常"),
    code_20000("success"),
    code_20001("参数对象为空"),
    code_20002("UID不能为空"),
    code_60001("UID对应的设备不存在"),
    code_60002("该设备已经绑定其他订单"),
    code_60003("业务订单号不能为空"),
    code_60004("没有对应的云服务"),
    code_60005("设备和卡密不匹配"),
    code_60006("设备已经激活"),
    code_60007("设备激活状态更新失败"),
    code_60008("充值卡已经激活"),
    code_60009("充值卡已过期"),
    code_60010("充值卡激活更新失败"),
    code_60011("一键激活失败"),
    code_60012("生成订单失败"),
    code_60013("订单列表为空"),
    code_60014("产品类型不存在"),
    code_60015("产品渠道不存在"),
    code_60016("产品价格不存在"),
    code_60017("产品信息不存在"),
    code_60018("token无效"),
    code_60019("订单重复提交异常"),
    code_60020("app的platform不存在"),
    code_60021("该产品不可重复订购"),
    code_60022("订单号不存在"),
    code_60023("paypal取消订阅失败"),
    code_60024("paypal根据token查询预交易详情失败"),
    code_60025("paypal创建订阅失败"),
    code_60026("订阅回调无此交易流水号"),
    code_60027("订阅回调发送MQ失败"),
    code_60028("苹果非自动续费交易明细已存在"),
    code_60029("苹果校验票据失败"),
    code_60030("苹果订阅交易流水号匹配失败"),
    code_60031("订单信息不存在"),
    code_60032("无权限查看订单信息"),
    code_60033("订阅回调失败"),
    code_60034("不符合一键购买的条件"),
    code_60035("同步回调修改支付状态失败"),
    code_60036("app_system不存在"),
    code_60037("票据定时任务补偿失败"),
    code_60038("创建订阅计划失败"),
    code_60039("修改订单失败"),
    code_60040("下单平台与支付平台不一致"),
    code_60041("一键购买下单失败"),
    code_60042("产品channel不存在或已下架"),
    code_60043("苹果订阅明细已存在"),
    code_60044("充值卡无效"),
    code_60045("升降级订单不存在"),
    code_60046("设备的uid不能为空"),
    code_60047("定时任务苹果订单无明细要补"),
    code_60048("续费订单手动续费生成订单号失败"),
    code_60049("新增交易明细失败"),
    code_60050("苹果后续明细依靠定时任务获取"),
    code_60051("产品已下架"),
    code_60100("stripe创建订阅失败"),
    code_60101("stripe错误"),
    code_60102("stripe取消订阅失败"),
    code_60103("stripe订阅不存在"),
    code_60200("预下单失败"),
    code_60201("激活码skuId不匹配不能叠加激活"),
    code_60202("过期订单不能激活叠加"),
    code_60203("不同平台的订单不能叠加"),
    code_60104("该订单不是paypal订阅订单"),
    code_60105("paypal订阅号为空"),
    code_60106("该paypal订单未支付不支持取消订阅"),
    code_60107("该paypal订单已经取消订阅"),
    code_60108("苹果票据不包含交易信息"),
    code_60109("订单退款数据写入失败"),
    code_60110("退款数据不存在或不完整"),
    code_60111("开票数据订单code不能为空"),
    code_60112("开票数据写入失败"),
    code_60113("新增sku失败"),
    code_60114("sku的id不能为空"),
    code_60115("产品信息更新失败"),
    code_60116("产品价格的国家不存在"),
    code_60117("新增sku的channel失败"),
    code_60118("产品价格sku不存在"),
    code_60119("产品sku和channel关联失败"),
    code_60120("新增sku价格失败"),
    code_60121("channel的id不能为空"),
    code_60122("channel的更新失败"),
    code_60123("请勿重复添加sku"),
    code_60124("channel下添加sku失败"),
    code_60125("channel下删除sku失败"),
    code_60126("stripe 用户不存在"),
    code_60127("暂不支持续费"),
    code_60128("暂不支持此平台激活"),
    code_60129("this code can only be used in US"),
    code_60130("该订阅已经被取消"),
    code_60131("paypal密钥为空"),
    code_60132("paypal订阅不存在"),
    code_60133("支付金额异常"),
    code_60204("该用户近7天没有过期的订单"),
    code_60205("用户权益兑换订单失败"),
    code_60301("卡卷转全量失败"),
    code_60302("卡卷信息不存在"),
    code_60303("卡卷不支持操作"),
    code_60304("region不存在"),
    code_60305("卡卷的名称不能为空"),
    code_60306("卡卷的领取时间设置错误"),
    code_60307("卡卷的使用时间设置错误"),
    code_60308("卡卷的使用金额不能为空"),
    code_60309("卡卷的折扣金额不能为空"),
    code_60310("卡卷信息保存失败"),
    code_60311("国家信息不能为空"),
    code_60312("存储标签失败"),
    code_60313("sku存储失败"),
    code_60314("国家信息保存失败"),
    code_60315("已领取"),
    code_60250("订单已续费自动续费"),
    code_60251("sku不匹配"),
    code_60252("sku支持续费"),
    code_60253("sku"),
    code_60260("无权限调用此接口"),
    code_60254("一分钟内请勿重复发送"),
    code_60255("验证码不匹配"),
    code_60256("当前时间不在活动有效时间范围内"),
    code_60257("补发兑换码失败"),
    code_60400("云存码创建编号失败"),
    code_60401("云存码创建失败"),
    code_60402("云存码生成失败"),
    code_60403("云存码存储失败"),
    code_60404("云存码存储更新状态失败"),
    code_60405("云存码未知的操作类型"),
    code_60406("激活码不可用"),
    code_60407("激活码已禁用"),
    code_60408("云存码不支持延长"),
    code_60409("云存码信息不存在"),
    code_60410("激活码已使用"),
    code_60411("发送次数超过5次"),
    code_60412("短信发送失败"),
    code_60413("无权限访问"),
    code_60414("手机号输入格式错误或手机号与上一次不一致"),
    code_60326("4接口调用失败"),
    code_60337("无权限访问"),
    code_60415("该用户不在白名单内"),
    code_60416("vlog下单失败"),
    code_60417("vlog白名单列表为空"),
    code_99999("系统异常，请稍后再试");


    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    ResponseEnum(String message) {
        this.message = message;
    }


    public <T> Result<T> result(T data) {
        Result<T> restResult = new Result<>();

        restResult.setCode(Integer.parseInt(this.name().substring(5)));
        restResult.setMsg(this.message);
        restResult.setData(data);
        return restResult;
    }

    public <T> Result<T> result() {
        Result<T> restResult = new Result<>();
        restResult.setCode(Integer.parseInt(this.name().substring(5)));
        restResult.setMsg(this.message);
        return restResult;
    }

    public <T> Result<T> result(String msg) {
        Result<T> restResult = new Result<>();
        restResult.setCode(Integer.parseInt(this.name().substring(5)));
        restResult.setMsg(msg);
        return restResult;
    }

    public static ResponseEnum fromTypeName(String typeName) {
        Map<String, ResponseEnum> resEnumMap = Stream.of(values()).
                collect(Collectors.toMap(ResponseEnum::getMessage, customBizTypeEnum -> customBizTypeEnum));
        if (resEnumMap.get(typeName) != null) {
            return resEnumMap.get(typeName);
        }
        return null;
    }


}
