package com.cxytiandi.elementui.service.impl;

import cn.hutool.Hutool;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ReUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cxytiandi.elementui.base.ResponseData;
import com.cxytiandi.elementui.dto.LoginFormDto;
import com.cxytiandi.elementui.dto.UserDto;
import com.cxytiandi.elementui.mapper.UserMapper;
import com.cxytiandi.elementui.model.User;
import com.cxytiandi.elementui.service.UserService;
import com.cxytiandi.elementui.utils.CheckUtil;
import com.cxytiandi.elementui.utils.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;


@Service("UserService")
public class UserServiceImpl implements UserService  {
    @Autowired
    UserMapper userMapper;

    @Override
    public ResponseData<Boolean> regist(LoginFormDto user) {
        return null;
    }

    @Override
    public ResponseData<Boolean> login(LoginFormDto loginForm) {
        String password = loginForm.getPassword();
        String username = loginForm.getUsername();

        UserDto userDto = new UserDto();
         userDto = (UserDto) ObjectUtil.copyAttribute(loginForm,userDto);
        User realUser = this.getUserBy(userDto);
        CheckUtil.check(realUser==null,"not exists user",realUser);
        String realUserPassword = realUser.getPassword();
        CheckUtil.check(realUserPassword.equals(password),"password error",password); //通过检测
        return ResponseData.ok(true);
    }

    public User getUserBy(UserDto user ) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.allEq(BeanUtil.beanToMap(user),false);
        return userMapper.selectOne(queryWrapper);
    }
}
