package com.zhou.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhou.reggie.dto.DishDto;
import com.zhou.reggie.entity.Category;
import com.zhou.reggie.entity.Dish;
import com.zhou.reggie.entity.DishFlavor;
import com.zhou.reggie.mapper.DishFlavorMapper;
import com.zhou.reggie.mapper.DishMapper;
import com.zhou.reggie.service.CategoryService;
import com.zhou.reggie.service.DishFlavorService;
import com.zhou.reggie.service.DishService;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author : ZhouMou
 * @date : 2022/12/24 22:51
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增菜品，同时插入菜品对应的口味数据，需要操作两张表：dish，dish_flavor
     */
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {

        // 保存菜品的基本信息到菜品表dish
        this.save(dishDto);

        Long dishId = dishDto.getId();

        // 保存菜品口味数据到菜品口味表dish_flavor
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);
    }

    /**
     * 根据id查询菜品及口味信息
     *
     * @param dishId
     * @return
     */
    @Override
    public DishDto getByIdWitchFlavor(Long dishId) {
        DishDto dishDto = new DishDto();
        Dish dish = dishMapper.selectById(dishId);

        BeanUtils.copyProperties(dish, dishDto);

        LambdaQueryWrapper<DishFlavor> lqw = new LambdaQueryWrapper<>();
        lqw.eq(dishId != null, DishFlavor::getDishId, dishId);
        lqw.orderByDesc(DishFlavor::getUpdateTime);
        List<DishFlavor> list = dishFlavorService.list(lqw);

        dishDto.setFlavors(list);
        return dishDto;
    }

    /**
     * 修改菜品信息
     *
     * @param dishDto
     */
    @Override
    @Transactional
    public void updateWithFlavor(DishDto dishDto) {
        // 更新dish表基本信息
        this.updateById(dishDto);
        // 清理当前菜品对应口味数据——dish_flavor
        LambdaQueryWrapper<DishFlavor> lqw = new LambdaQueryWrapper<>();
        lqw.eq(DishFlavor::getDishId, dishDto.getId());
        dishFlavorService.remove(lqw);
        // 更新dish_flavor表菜品信息
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);
    }

    /**
     * 删除菜品信息
     *
     * @param ids
     */
    @Override
    public void deleteByIds(Long[] ids) {

        for (Long id : ids) {
            LambdaQueryWrapper<DishFlavor> lqw = new LambdaQueryWrapper<>();
            lqw.eq(DishFlavor::getDishId, id);
            dishFlavorService.remove(lqw);
            dishMapper.deleteById(id);
        }
    }

    /**
     * 起售，停售菜品
     *
     * @param status
     * @param ids
     */
    @Override
    public void updateStatus(Integer status, Long[] ids) {
        for (Long id : ids) {
            Dish updateDish = new Dish();
            Dish dish = this.getById(id);
            BeanUtils.copyProperties(dish, updateDish, "status");
            updateDish.setStatus(status);
            dishMapper.updateById(updateDish);
        }
    }

    /**
     * 在菜品管理添加套餐时展示
     *
     * @param dish
     * @return
     */
    @Override
    public List<DishDto> getList(Dish dish) {
        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
        lqw.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
        lqw.eq(Dish::getStatus, 1);
        lqw.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        List<Dish> list = dishMapper.selectList(lqw);
        List<DishDto> dtoList = new ArrayList<>();
        for (Dish dish1 : list) {
            DishDto dishDto = new DishDto();
            LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(DishFlavor::getDishId, dish1.getId());
            BeanUtils.copyProperties(dish1, dishDto);
            List<DishFlavor> dishFlavors = dishFlavorMapper.selectList(lambdaQueryWrapper);
            dishDto.setFlavors(dishFlavors);
            dtoList.add(dishDto);
        }
        return dtoList;
    }

    /**
     * 分页查询菜品信息
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @Override
    public Page<DishDto> setPage(int page, int pageSize, String name) {
        Page<Dish> pageInfo = new Page<>(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>();
        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
        lqw.like(StringUtils.isNotEmpty(name), Dish::getName, name);
        lqw.orderByDesc(Dish::getUpdateTime);

        this.page(pageInfo, lqw);
        BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");

        List<Dish> records = pageInfo.getRecords();
        List<DishDto> list = records.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            // 将item的其他属性给dishDto
            BeanUtils.copyProperties(item, dishDto);
            // 分类ID
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);

            if (category != null) {
                String categoryName = category.getName();
                // 将CategoryName赋值给dishDto
                dishDto.setCategoryName(categoryName);
            }

            return dishDto;
        }).collect(Collectors.toList());

        dishDtoPage.setRecords(list);
        return dishDtoPage;
    }
}
