package com.zhou.reggie.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhou.reggie.common.R;
import com.zhou.reggie.dto.OrdersDto;
import com.zhou.reggie.entity.Orders;
import com.zhou.reggie.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author : ZhouMou
 * @date : 2022/12/29 21:40
 */
@RestController
@Slf4j
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 用户下单
     *
     * @param orders
     * @return
     */
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders) {
        orderService.submit(orders);
        return R.success("下单成功");
    }

    /**
     * 查询订单数据
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/userPage")
    public R<Page<OrdersDto>> page(int page, int pageSize) {
        Page<OrdersDto> pageInfo = orderService.setPage(page, pageSize);
        return R.success(pageInfo);
    }
}
