package com.zhou.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhou.reggie.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author : ZhouMou
 * @date : 2022/12/29 13:54
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
