package com.cxytiandi.elementui.service;

import com.cxytiandi.elementui.base.ResponseData;
import com.cxytiandi.elementui.dto.LoginFormDto;
import com.cxytiandi.elementui.model.User;
import org.springframework.stereotype.Service;


public interface UserService {
    ResponseData<Boolean> regist(LoginFormDto user);

    ResponseData<Boolean> login(LoginFormDto loginForm);

}
