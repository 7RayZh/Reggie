package com.zhou.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhou.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author : ZhouMou
 * @date : 2022/12/22 19:52
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
