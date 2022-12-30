package com.zhou.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sun.org.apache.xpath.internal.operations.Or;
import com.zhou.reggie.dto.OrdersDto;
import com.zhou.reggie.entity.Orders;

/**
 * @author : ZhouMou
 * @date : 2022/12/29 21:42
 */
public interface OrderService extends IService<Orders> {
    /**
     * 用户下单
     *
     * @param orders
     */
    void submit(Orders orders);

    /**
     * 查询订单数据
     * @param page
     * @param pageSize
     * @return
     */
    Page<OrdersDto> setPage(int page, int pageSize);
}
