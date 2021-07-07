package com.karsa.service.impl;

import com.karsa.entity.OrderInfo;
import com.karsa.mapper.OrderInfoMapper;
import com.karsa.service.IOrderInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author karsa
 * @since 2021-07-07
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements IOrderInfoService {

}
