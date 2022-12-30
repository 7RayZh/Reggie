package com.zhou.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhou.reggie.dto.DishDto;
import com.zhou.reggie.entity.Dish;
import com.zhou.reggie.mapper.DishMapper;

import java.util.List;

/**
 * @author : ZhouMou
 * @date : 2022/12/24 22:50
 */

public interface DishService extends IService<Dish> {
    /**
     * 新增菜品，同时插入菜品对应的口味数据，需要操作两张表：dish，dish_flavor
     *
     * @param dishDto
     */
    void saveWithFlavor(DishDto dishDto);

    /**
     * 根据id查询菜品及口味信息
     *
     * @param dishId
     * @return
     */
    DishDto getByIdWitchFlavor(Long dishId);

    /**
     * 修改菜品信息
     *
     * @param dishDto
     */
    void updateWithFlavor(DishDto dishDto);

    /**
     * 删除菜品信息
     *
     * @param ids
     */
    void deleteByIds(Long[] ids);

    /**
     * 起售，停售菜品
     *
     * @param status
     * @param ids
     */
    void updateStatus(Integer status, Long[] ids);

    /**
     * 在菜品管理添加套餐时展示
     *
     * @param dish
     * @return
     */
    List<DishDto> getList(Dish dish);

    /**
     * 分页查询菜品信息
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    Page<DishDto> setPage(int page, int pageSize, String name);
}
