package com.cxytiandi.elementui.service;

import com.cxytiandi.elementui.base.ResponseData;
import com.cxytiandi.elementui.dto.LoginFormDto;
import com.cxytiandi.elementui.dto.UserDto;
import com.cxytiandi.elementui.model.User;
import org.springframework.stereotype.Service;


public interface UserService {
    ResponseData<Integer> regist(UserDto userDto);

    ResponseData<Boolean> login(LoginFormDto loginForm);

    ResponseData<Integer> update(UserDto userDto);
}
