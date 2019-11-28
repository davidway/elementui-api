package com.cxytiandi.elementui.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxytiandi.elementui.base.ResponseData;
import com.cxytiandi.elementui.dto.HabbitDto;
import com.cxytiandi.elementui.mapper.HabbitMapper;
import com.cxytiandi.elementui.model.Habbit;
import com.cxytiandi.elementui.model.User;
import com.cxytiandi.elementui.service.HabbitService;
import com.cxytiandi.elementui.utils.ObjectUtil;
import com.cxytiandi.elementui.vo.HabbitVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("HabbitService")
public class HabbitServiceImpl implements HabbitService {
    @Autowired
    public HabbitMapper habbitMapper;

    @Override
    public ResponseData<Integer> add(HabbitDto habbitDto) {
        Habbit habbit = new Habbit();
        habbit = (Habbit) ObjectUtil.copyAttribute(habbitDto,habbit);
        int habbitId  = habbitMapper.insert(habbit);
        return ResponseData.ok(habbitId);
    }

    @Override
    public ResponseData<Integer> update(HabbitDto habbitDto) {
        Habbit habbit = new Habbit();
        habbit = (Habbit) ObjectUtil.copyAttribute(habbitDto,habbit);
        int habbitId  = habbitMapper.updateById(habbit);
        return ResponseData.ok(habbitId);
    }

    @Override
    public ResponseData<List<Habbit>> list(HabbitDto habbitDto, Page<Habbit> page) {

        QueryWrapper queryWrapper = new QueryWrapper<Habbit>();
        queryWrapper.eq("user_id",habbitDto.getUserId());

        IPage<Habbit> habbitPage = habbitMapper.selectPage(page, queryWrapper );

       return ResponseData.ok(habbitPage.getRecords());
    }
}
