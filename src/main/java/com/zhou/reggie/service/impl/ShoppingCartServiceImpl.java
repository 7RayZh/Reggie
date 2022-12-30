package com.zhou.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhou.reggie.common.BaseContext;
import com.zhou.reggie.common.R;
import com.zhou.reggie.entity.ShoppingCart;
import com.zhou.reggie.mapper.ShoppingCartMapper;
import com.zhou.reggie.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author : ZhouMou
 * @date : 2022/12/29 20:06
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    /**
     * 添加到购物车
     *
     * @param shoppingCart
     * @return
     */
    @Override
    public ShoppingCart add(ShoppingCart shoppingCart) {

        // 设置用户Id，指定当前是哪个用户的购物车
        Long id = BaseContext.getCurrentId();
        shoppingCart.setUserId(id);

        // 查询套餐或菜品是否在购物车
        Long dishId = shoppingCart.getDishId();

        LambdaQueryWrapper<ShoppingCart> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ShoppingCart::getUserId, id);

        if (dishId != null) {
            // 添加到购物车的是菜品
            lqw.eq(ShoppingCart::getDishId, dishId);
        } else {
            // 添加到购物车的是套餐
            lqw.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }

        ShoppingCart cart = shoppingCartMapper.selectOne(lqw);
        if (cart != null) {
            // 如果存在，则在原来基础上+1
            Integer number = cart.getNumber();
            cart.setNumber(number + 1);
            shoppingCartMapper.updateById(cart);
        } else {
            // 如果不存在，加入到购物车，数量默认1
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartMapper.insert(shoppingCart);
            cart = shoppingCart;
        }
        return cart;
    }

    /**
     * 商品-1
     *
     * @param shoppingCart
     * @return
     */
    @Override
    public ShoppingCart sub(ShoppingCart shoppingCart) {
        Long id = BaseContext.getCurrentId();
        LambdaQueryWrapper<ShoppingCart> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ShoppingCart::getUserId, id);

        Long dishId = shoppingCart.getDishId();
        if (dishId != null) {
            lqw.eq(ShoppingCart::getDishId, dishId);
        } else {
            lqw.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }

        ShoppingCart cart = shoppingCartMapper.selectOne(lqw);
        Integer number = cart.getNumber();
        if (number == 1) {
            shoppingCartMapper.delete(lqw);
            cart.setNumber(0);
            return cart;
        } else {
            cart.setNumber(number - 1);
            shoppingCartMapper.updateById(cart);
        }
        return cart;
    }

    /**
     * 查看购物车
     *
     * @return
     */
    @Override
    public List<ShoppingCart> getList() {
        Long currentId = BaseContext.getCurrentId();
        LambdaQueryWrapper<ShoppingCart> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ShoppingCart::getUserId, currentId);
        lqw.orderByDesc(ShoppingCart::getCreateTime);
        return shoppingCartMapper.selectList(lqw);
    }

    /**
     * 清空购物车
     */
    @Override
    public void clean() {
        Long currentId = BaseContext.getCurrentId();
        LambdaQueryWrapper<ShoppingCart> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ShoppingCart::getUserId, currentId);
        shoppingCartMapper.delete(lqw);
    }
}
