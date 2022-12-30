package com.zhou.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhou.reggie.dto.SetmealDto;
import com.zhou.reggie.entity.Setmeal;
import com.zhou.reggie.entity.SetmealDish;

import java.util.List;

/**
 * @author : ZhouMou
 * @date : 2022/12/24 22:53
 */
public interface SetmealService extends IService<Setmeal> {
    /**
     * 分页查询信息
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    Page<SetmealDto> pageInfo(int page, int pageSize, String name);

    /**
     * 删除套餐
     * @param ids
     * @return
     */
    void deleteByIds(Long[] ids);

    /**
     * 启售，停售套餐
     *
     * @param status
     * @param ids
     */
    void updateStatus(Integer status, Long[] ids);

    /**
     * 新增套餐
     *
     * @param setmealDto
     */
    void saveDto(SetmealDto setmealDto);

    /**
     * 前端查询套餐信息
     *
     * @param setmeal
     * @return
     */
    List<Setmeal> getList(Setmeal setmeal);

    /**
     * 套餐内信息
     * @param setmealId
     * @return
     */
    List<SetmealDish> getDish(Long setmealId);
}
