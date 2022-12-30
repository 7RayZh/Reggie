package com.zhou.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.zhou.reggie.common.R;
import com.zhou.reggie.entity.User;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author : ZhouMou
 * @date : 2022/12/29 13:54
 */
public interface UserService extends IService<User> {
    /**
     * 用户登录
     *
     * @param map
     * @param session
     * @return
     */
    User login(Map map, HttpSession session);

    /**
     * 发送短信验证码
     *
     * @param user
     * @param session
     * @return
     */
    boolean sendMsg(User user, HttpSession session);
}
