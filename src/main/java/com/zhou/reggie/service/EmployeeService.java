package com.zhou.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhou.reggie.entity.Employee;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : ZhouMou
 * @date : 2022/12/22 19:52
 */
public interface EmployeeService extends IService<Employee> {

    /**
     * 员工信息分页查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    Page<Employee> setPage(int page, int pageSize, String name);
}
