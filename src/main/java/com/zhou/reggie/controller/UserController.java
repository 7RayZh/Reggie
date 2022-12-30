package com.zhou.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhou.reggie.common.R;
import com.zhou.reggie.entity.User;
import com.zhou.reggie.service.UserService;
import com.zhou.reggie.utils.SMSUtils;
import com.zhou.reggie.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author : ZhouMou
 * @date : 2022/12/29 13:55
 */
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 发送手机验证码
     *
     * @param user
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session) {
        boolean result = userService.sendMsg(user, session);
        if (result) {
            return R.success("短信发送成功");
        } else {
            return R.error("短信发送失败");
        }
    }

    /**
     * 发送手机验证码
     *
     * @param map
     * @return
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session) {

        User user = userService.login(map, session);
        if (user != null) {
            return R.success(user);
        } else {
            return R.error("登录失败");
        }
    }
}
