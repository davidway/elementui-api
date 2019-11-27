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
    public ResponseData<Integer> regist(UserDto user) {
        if ( this.checkUserExists(user) ){
            return ResponseData.fail("账号已存在");
        }
        ResponseData<Integer> result = this.addUser(user);

        return result;
    }

    public boolean checkUserExists(UserDto userDto) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",userDto.getUsername());
        User user =  userMapper.selectOne(queryWrapper);
        if ( user!=null){
            return true;
        }else{
            return false;
        }
    }

    public ResponseData<Integer> addUser(UserDto userDto) {
        User user = new User();
        user = (User) ObjectUtil.copyAttribute(userDto,user);
        int userId  = userMapper.insert(user);
        return ResponseData.ok(userId);
    }

    @Override
    public ResponseData<Boolean> login(LoginFormDto loginForm) {
        String password = loginForm.getPassword();
        String username = loginForm.getUsername();

        UserDto userDto = new UserDto();
         userDto = (UserDto) ObjectUtil.copyAttribute(loginForm,userDto);
        User realUser = this.getUserBy(userDto);
        CheckUtil.check(realUser!=null,"not exists user",realUser);
        String realUserPassword = realUser.getPassword();
        CheckUtil.check(realUserPassword.equals(password),"password error",password);
        return ResponseData.ok(true);
    }

    public User getUserBy(UserDto userDto ) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",userDto.getUsername());
        User user =  userMapper.selectOne(queryWrapper);
        return user;
    }
}
