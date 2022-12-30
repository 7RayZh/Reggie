package com.zhou.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhou.reggie.entity.Category;

import java.util.List;

/**
 * @author : ZhouMou
 * @date : 2022/12/24 22:17
 */
public interface CategoryService extends IService<Category> {

    public void remove(Long id);

    /**
     * 分页查询菜品
     *
     * @param page
     * @param pageSize
     * @return
     */
    Page<Category> setPage(int page, int pageSize);

    /**
     * 下拉菜单中的菜品展示
     *
     * @param category
     * @return
     */
    List<Category> getList(Category category);
}
