package com.zhou.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhou.reggie.common.R;
import com.zhou.reggie.dto.DishDto;
import com.zhou.reggie.entity.Category;
import com.zhou.reggie.entity.Dish;
import com.zhou.reggie.entity.DishFlavor;
import com.zhou.reggie.service.CategoryService;
import com.zhou.reggie.service.DishFlavorService;
import com.zhou.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 菜品管理
 *
 * @author : ZhouMou
 * @date : 2022/12/27 8:45
 */
@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    /**
     * 分页查询菜品信息
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page<DishDto>> page(int page, int pageSize, String name) {
        Page<DishDto> dishDtoPage = dishService.setPage(page, pageSize, name);
        return R.success(dishDtoPage);
    }

    /**
     * 新增菜品
     *
     * @param dishDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto) {
        dishService.saveWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }

    /**
     * 根据id查询菜品信息和对应的口味
     *
     * @param dishId
     * @return
     */
    @GetMapping("/{dishId}")
    public R<DishDto> getDish(@PathVariable Long dishId) {
        DishDto dishDto = dishService.getByIdWitchFlavor(dishId);
        return R.success(dishDto);
    }

    /**
     * 修改菜品信息
     *
     * @param dishDto
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto) {
        dishService.updateWithFlavor(dishDto);
        return R.success("修改菜品成功");
    }

    @DeleteMapping
    public R<String> delete(Long[] ids) {
        dishService.deleteByIds(ids);
        return R.success("删除菜品成功");
    }

    @PostMapping("/status/{status}")
    public R<String> updateStatus(@PathVariable Integer status, Long[] ids) {
        dishService.updateStatus(status, ids);
        if (status == 0) {
            return R.success("停售菜品成功");
        } else {
            return R.success("启售菜品成功");
        }
    }


    /**
     * 在菜品管理添加套餐时展示
     *
     * @param dish
     * @return
     */
    @GetMapping("/list")
    public R<List<DishDto>> getList(Dish dish) {
        List<DishDto> list = dishService.getList(dish);
        return R.success(list);
    }

}
