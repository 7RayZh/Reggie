package com.zhou.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhou.reggie.entity.AddressBook;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author : ZhouMou
 * @date : 2022/12/29 18:19
 */
@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {
}
