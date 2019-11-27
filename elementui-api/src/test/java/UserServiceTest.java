import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.SecureUtil;
import com.cxytiandi.elementui.App;
import com.cxytiandi.elementui.base.ResponseData;
import com.cxytiandi.elementui.dto.LoginFormDto;
import com.cxytiandi.elementui.dto.RegistFormDto;
import com.cxytiandi.elementui.dto.UserDto;
import com.cxytiandi.elementui.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)

public class UserServiceTest {
    @Autowired
    UserService userService;

    @Test
    public void testMD5(){
        UserDto userDto = new UserDto();
        String username="249261450@qq.com";
        userDto.setUsername(username);
        String password = "zhuweiqiang1130";
        password = SecureUtil.md5(password);
            System.out.println(password);
    }
    @Test
    public void testUserRegist(){
        UserDto userDto = new UserDto();
        String username="249261450@qq.com";
        userDto.setUsername(username);
        String password = "zhuweiqiang1130";
        password = SecureUtil.md5(password);

        userDto.setPassword(password);
        userDto.setAddress("东莞-横沥");
        userDto.setDate(DateUtil.formatDate(new Date()));
        userDto.setName("伟强-朱");
        ResponseData<Integer> result = userService.regist(userDto);

        Assert.assertEquals(result.getData(),1);
    }
    @Test
    public void testUserLogin(){
        LoginFormDto loginForm = new LoginFormDto();
        String username="249261450@qq.com";
        loginForm.setUsername(username);
        String password = "zhuweiqiang1130";
        password = SecureUtil.md5(password);
        loginForm.setPassword(password);
        ResponseData<Boolean> result = userService.login(loginForm);

        ResponseData<Boolean> myExpert = new ResponseData<Boolean>(true);
        Assert.assertEquals(result,myExpert);
    }


}
