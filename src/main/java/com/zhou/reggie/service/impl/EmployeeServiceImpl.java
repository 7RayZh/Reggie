package com.zhou.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhou.reggie.common.R;
import com.zhou.reggie.entity.Employee;
import com.zhou.reggie.mapper.EmployeeMapper;
import com.zhou.reggie.service.EmployeeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : ZhouMou
 * @date : 2022/12/22 19:52
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工信息分页查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @Override
    public Page<Employee> setPage(int page, int pageSize, String name) {
        // 构建分页构造器
        Page<Employee> pageInfo = new Page<>(page, pageSize);

        // 构造条件构造器
        LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper<>();
        // 添加过滤条件
        lqw.like(StringUtils.isNotEmpty(name), Employee::getName, name);
        // 添加排序条件
        lqw.orderByDesc(Employee::getUpdateTime);

        // 执行查询
        this.page(pageInfo, lqw);
        return pageInfo;
    }
}
