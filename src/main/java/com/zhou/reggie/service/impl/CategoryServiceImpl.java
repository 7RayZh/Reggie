package com.zhou.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhou.reggie.common.CustomException;
import com.zhou.reggie.entity.Category;
import com.zhou.reggie.entity.Dish;
import com.zhou.reggie.entity.Setmeal;
import com.zhou.reggie.mapper.CategoryMapper;
import com.zhou.reggie.mapper.DishMapper;
import com.zhou.reggie.service.CategoryService;
import com.zhou.reggie.service.DishService;
import com.zhou.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : ZhouMou
 * @date : 2022/12/24 22:19
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    @Override
    public void remove(Long id) {

        LambdaQueryWrapper<Dish> lqw_dish = new LambdaQueryWrapper<>();
        lqw_dish.eq(Dish::getCategoryId, id);
        int count_dish = dishService.count(lqw_dish);
        // 查询当前分类是否关联了菜品，如果已经关联，抛出一个业务异常
        if (count_dish > 0) {
            throw new CustomException("当前分类下关联了菜品,不能删除");
            // 已经关联菜品,抛出一个业务异常
        }


        LambdaQueryWrapper<Setmeal> lqw_setmeal = new LambdaQueryWrapper<>();
        lqw_setmeal.eq(Setmeal::getCategoryId, id);
        int count_setmeal = setmealService.count(lqw_setmeal);
        // 查询当前分类是否关联了套餐，如果已经关联，抛出一个业务异常
        if (count_setmeal > 0) {
            // 已经关联套餐,抛出一个业务异常
            throw new CustomException("当前分类下关联了套餐,不能删除");
        }

        // 正常删除
        super.removeById(id);
    }

    /**
     * 分页查询菜品
     *
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public Page<Category> setPage(int page, int pageSize) {
        Page<Category> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
        lqw.orderByAsc(Category::getSort);

        this.page(pageInfo, lqw);
        return pageInfo;
    }

    /**
     * 下拉菜单中的菜品展示
     *
     * @param category
     * @return
     */
    @Override
    public List<Category> getList(Category category) {
        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
        lqw.eq(category.getType() != null, Category::getType, category.getType());
        lqw.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List<Category> list = this.list(lqw);
        return list;
    }
}
