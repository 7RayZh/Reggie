package com.zhou.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhou.reggie.common.R;
import com.zhou.reggie.dto.SetmealDto;
import com.zhou.reggie.entity.Category;
import com.zhou.reggie.entity.Setmeal;
import com.zhou.reggie.entity.SetmealDish;
import com.zhou.reggie.service.CategoryService;
import com.zhou.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author : ZhouMou
 * @date : 2022/12/29 10:09
 */
@Slf4j
@RestController
@RequestMapping("setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;


    /**
     * 分页查询套餐信息
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page<SetmealDto>> page(int page, int pageSize, String name) {

        Page<SetmealDto> setmealDtoPage = setmealService.pageInfo(page, pageSize, name);
        return R.success(setmealDtoPage);
    }

    /**
     * 删除套餐信息
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(Long[] ids) {
        setmealService.deleteByIds(ids);

        return R.success("删除套餐成功");

    }

    /**
     * 启售，停售套餐
     *
     * @param ids
     * @return
     */
    @PostMapping("/status/{status}")
    public R<String> updateStatus(@PathVariable Integer status, Long[] ids) {
        setmealService.updateStatus(status, ids);
        if (status == 0) {
            return R.success("停售套餐成功");
        } else {
            return R.success("启售套餐成功");
        }
    }

    /**
     * 新增套餐
     *
     * @param setmealDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto) {
        setmealService.saveDto(setmealDto);

        return R.success("新增菜品成功");
    }

    @GetMapping("/list")
    public R<List<Setmeal>> getList(Setmeal setmeal) {
        List<Setmeal> list = setmealService.getList(setmeal);
        return R.success(list);
    }

    @GetMapping("/dish/{setmealId}")
    public R<List<SetmealDish>> getDish(@PathVariable Long setmealId) {
        List<SetmealDish> list = setmealService.getDish(setmealId);
        return R.success(list);
    }
}
