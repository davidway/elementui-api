import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxytiandi.elementui.App;
import com.cxytiandi.elementui.base.ResponseData;
import com.cxytiandi.elementui.dto.HabbitDto;
import com.cxytiandi.elementui.dto.LoginFormDto;
import com.cxytiandi.elementui.mapper.HabbitMapper;
import com.cxytiandi.elementui.mapper.UserMapper;
import com.cxytiandi.elementui.model.Habbit;
import com.cxytiandi.elementui.model.User;
import com.cxytiandi.elementui.service.HabbitService;
import com.cxytiandi.elementui.service.UserService;
import com.cxytiandi.elementui.vo.HabbitVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@Slf4j
public class CommentServiceTest {


    @Autowired
    private HabbitService habbitService;

    @Test
    public void testAddHabbit(){
        //arrage
        HabbitDto habbitDto = new HabbitDto();
        habbitDto.setContent("我的第二个习惯");
        habbitDto.setTitle("测试");
        int userId=1;
        habbitDto.setUserId(userId);
        //act
        ResponseData<Integer> result = habbitService.add(habbitDto);

        ResponseData<Integer> expert = new ResponseData<Integer>();
        expert.setData(1);
        //check
        log.info("result={},expert={}",result,expert);
    }

    @Test
    public void testUpdateHabbit(){
        //arrage
        HabbitDto habbitDto = new HabbitDto();
        habbitDto.setId(1);
        habbitDto.setMoney(new BigDecimal("98.99"));
        habbitDto.setContent("1");
        habbitDto.setTitle("测试");
        int userId=1;
        habbitDto.setUserId(userId);
        //act
        ResponseData<Integer> result = habbitService.update(habbitDto);

        ResponseData<Integer> expert = new ResponseData<Integer>();
        expert.setData(1);
        //check

    }

    @Test
    public void testGetHabbitList(){
        System.out.println("----- baseMapper 自带分页 ------");
        Page<Habbit> page = new Page<>(0, 10);
        HabbitDto habbitDto = new HabbitDto();
        habbitDto.setUserId(1);
        ResponseData<List<Habbit>> habbitVoList =  habbitService.list(habbitDto,page);
        log.info("habbitList ： {}",habbitVoList);
    }



}