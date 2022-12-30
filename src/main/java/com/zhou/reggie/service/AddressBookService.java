package com.zhou.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhou.reggie.entity.AddressBook;

import java.util.List;

/**
 * @author : ZhouMou
 * @date : 2022/12/29 18:19
 */
public interface AddressBookService extends IService<AddressBook> {
    /**
     * 查询地址信息
     * @param addressBook
     * @return
     */
    List<AddressBook> getList(AddressBook addressBook);

    /**
     * 查询默认地址
     * @return
     */
    AddressBook getDefault();

    /**
     * 设置默认地址
     * @param addressBook
     * @return
     */
    AddressBook setDefault(AddressBook addressBook);
}
