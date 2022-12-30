package com.zhou.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhou.reggie.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author : ZhouMou
 * @date : 2022/12/29 21:41
 */
@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
}
