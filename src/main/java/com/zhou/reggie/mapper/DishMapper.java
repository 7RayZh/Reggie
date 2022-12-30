package com.zhou.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhou.reggie.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author : ZhouMou
 * @date : 2022/12/24 22:50
 */
@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
