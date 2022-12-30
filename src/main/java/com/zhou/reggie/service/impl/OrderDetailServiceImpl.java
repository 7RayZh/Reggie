package com.zhou.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhou.reggie.entity.OrderDetail;
import com.zhou.reggie.mapper.OrderDetailMapper;
import com.zhou.reggie.service.OrderDetailService;
import org.springframework.stereotype.Service;

/**
 * @author : ZhouMou
 * @date : 2022/12/29 21:45
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
