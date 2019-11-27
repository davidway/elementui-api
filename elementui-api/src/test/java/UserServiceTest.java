import cn.hutool.crypto.SecureUtil;
import com.cxytiandi.elementui.App;
import com.cxytiandi.elementui.base.ResponseData;
import com.cxytiandi.elementui.dto.LoginFormDto;
import com.cxytiandi.elementui.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)

public class UserServiceTest {
    @Autowired
    UserService userService;

    @Test
    public void testUserService(){
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
