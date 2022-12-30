package com.zhou.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhou.reggie.common.R;
import com.zhou.reggie.entity.User;
import com.zhou.reggie.mapper.UserMapper;
import com.zhou.reggie.service.UserService;
import com.zhou.reggie.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author : ZhouMou
 * @date : 2022/12/29 13:55
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    /**
     * 用户登录
     *
     * @param map
     * @param session
     * @return
     */
    @Override
    public User login(Map map, HttpSession session) {
        // 从Map中获取手机号和验证码
        String phone = map.get("phone").toString();
        String code = map.get("code").toString();

        // 从Session中获取保存的验证码
        Object codeInSession = session.getAttribute(phone);
        // 进行验证码的比对
        if (codeInSession != null && codeInSession.equals(code)) {
            // 如果能够比对成功，说明登录成功
            LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
            lqw.eq(User::getPhone, phone);
            User user = this.getOne(lqw);
            if (user == null) {
                // 判断当前手机号对应的用户是否为新用户，如果是新用户就自动完成注册
                user = new User();
                user.setPhone(phone);
                this.save(user);
            }
            session.setAttribute("user", user.getId());
            return user;
        }

        return null;
    }

    /**
     * 发送短信验证码
     *
     * @param user
     * @param session
     * @return
     */
    @Override
    public boolean sendMsg(User user, HttpSession session) {
        // 获取手机号
        String phone = user.getPhone();
        if (StringUtils.isNotEmpty(phone)) {
            // 生成随机的4位验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info(code);
            // 调用阿里云api
//            SMSUtils.sendMessage("瑞吉外卖", "SMS_266780189", phone, code);
            // 将生成的验证码保存到Session
            session.setAttribute(phone, code);
            return true;
        }
        return false;
    }
}
