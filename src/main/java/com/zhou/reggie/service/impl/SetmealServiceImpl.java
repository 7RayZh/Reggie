package com.zhou.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhou.reggie.common.CustomException;
import com.zhou.reggie.dto.SetmealDto;
import com.zhou.reggie.entity.Category;
import com.zhou.reggie.entity.Setmeal;
import com.zhou.reggie.entity.SetmealDish;
import com.zhou.reggie.mapper.SetmealDishMapper;
import com.zhou.reggie.mapper.SetmealMapper;
import com.zhou.reggie.service.CategoryService;
import com.zhou.reggie.service.SetmealDishService;
import com.zhou.reggie.service.SetmealService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : ZhouMou
 * @date : 2022/12/24 22:57
 */
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    /**
     * 分页查询信息
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @Override
    public Page<SetmealDto> pageInfo(int page, int pageSize, String name) {
        Page<Setmeal> pageInfo = new Page<>(page, pageSize);
        Page<SetmealDto> setmealDtoPage = new Page<>();

        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();
        lqw.like(StringUtils.isNotEmpty(name), Setmeal::getName, name);

        this.page(pageInfo, lqw);

        BeanUtils.copyProperties(pageInfo, setmealDtoPage, "records");
        List<Setmeal> records = pageInfo.getRecords();
        List<SetmealDto> list = records.stream().map((item) -> {
            SetmealDto dto = new SetmealDto();
            BeanUtils.copyProperties(item, dto);

            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);

            if (category != null) {
                String categoryName = category.getName();
                dto.setCategoryName(categoryName);
            }

            return dto;
        }).collect(Collectors.toList());

        setmealDtoPage.setRecords(list);
        return setmealDtoPage;
    }

    /**
     * 删除套餐信息
     *
     * @param ids
     */
    @Override
    @Transactional
    public void deleteByIds(Long[] ids) {

        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();
        lqw.in(Setmeal::getId, Arrays.asList(ids));
        lqw.eq(Setmeal::getStatus, 1);
        int count = this.count(lqw);
        if (count > 0) {
            throw new CustomException("套餐正在售卖中，不能删除");
        }

        for (Long id : ids) {
            Setmeal setmeal = this.getById(id);
            LambdaQueryWrapper<SetmealDish> lqw1 = new LambdaQueryWrapper<>();
            lqw1.eq(SetmealDish::getSetmealId, id);
            setmealDishService.remove(lqw1);
            this.removeById(id);
        }
    }

    /**
     * 启售，停售套餐
     *
     * @param status
     * @param ids
     */
    @Override
    public void updateStatus(Integer status, Long[] ids) {
        for (Long id : ids) {
            Setmeal setmeal = new Setmeal();
            Setmeal setmeal_old = this.getById(id);
            BeanUtils.copyProperties(setmeal_old, setmeal, "status");
            setmeal.setStatus(status);
            this.updateById(setmeal);
        }
    }

    /**
     * 新增套餐
     *
     * @param setmealDto
     */
    @Override
    public void saveDto(SetmealDto setmealDto) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDto, setmeal);
        this.save(setmeal);
        for (SetmealDish setmealDish : setmealDto.getSetmealDishes()) {
            SetmealDish set = new SetmealDish();
            BeanUtils.copyProperties(setmealDish, set);
            set.setSetmealId(setmeal.getId());
            setmealDishService.save(set);
        }
    }

    /**
     * 前端查询套餐信息
     *
     * @param setmeal
     * @return
     */
    @Override
    public List<Setmeal> getList(Setmeal setmeal) {
        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Setmeal::getCategoryId, setmeal.getCategoryId());
        lqw.eq(Setmeal::getStatus, setmeal.getStatus());
        lqw.orderByDesc(Setmeal::getUpdateTime);
        List<Setmeal> list = setmealMapper.selectList(lqw);
        return list;
    }

    /**
     * 套餐内信息
     *
     * @param setmealId
     * @return
     */
    @Override
    public List<SetmealDish> getDish(Long setmealId) {

        LambdaQueryWrapper<SetmealDish> lqw = new LambdaQueryWrapper<>();
        lqw.eq(SetmealDish::getSetmealId, setmealId);
//        List<SetmealDto> list = new ArrayList<>();
//        for (SetmealDish setmealDish : setmealDishMapper.selectList(lqw)) {
//            SetmealDto setmealDto = new SetmealDto();
//            Setmeal setmeal = setmealMapper.selectById(setmealDish.getDishId());
//            System.out.println(setmeal);
//            BeanUtils.copyProperties(setmeal, setmealDto);
//            BeanUtils.copyProperties(setmealDish, setmealDto);
//        }

        return setmealDishMapper.selectList(lqw);
    }
}

