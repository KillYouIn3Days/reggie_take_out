package com.itheima.reggie.service.impl;

import com.itheima.reggie.entity.Orders;
import com.itheima.reggie.mapper.OrdersMapper;
import com.itheima.reggie.service.OrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author WilsonEdwards
 * @since 2022-05-24
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

}
