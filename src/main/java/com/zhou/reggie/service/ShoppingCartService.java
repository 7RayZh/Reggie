package com.zhou.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhou.reggie.entity.ShoppingCart;

import java.util.List;

/**
 * @author : ZhouMou
 * @date : 2022/12/29 20:05
 */
public interface ShoppingCartService extends IService<ShoppingCart> {
    /**
     * 添加到购物车
     *
     * @param shoppingCart
     * @return
     */
    ShoppingCart add(ShoppingCart shoppingCart);

    /**
     * 商品-1
     *
     * @param shoppingCart
     * @return
     */
    ShoppingCart sub(ShoppingCart shoppingCart);

    /**
     * 查看购物车
     * @return
     */
    List<ShoppingCart> getList();

    /**
     * 清空购物车
     */
    void clean();
}
