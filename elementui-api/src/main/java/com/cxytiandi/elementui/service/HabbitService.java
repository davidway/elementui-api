package com.cxytiandi.elementui.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxytiandi.elementui.base.ResponseData;
import com.cxytiandi.elementui.dto.HabbitDto;
import com.cxytiandi.elementui.model.Habbit;
import com.cxytiandi.elementui.model.User;
import com.cxytiandi.elementui.vo.HabbitVo;

import java.util.List;

public interface HabbitService {
    ResponseData<Integer> add(HabbitDto habbitDto);

    ResponseData<Integer> update(HabbitDto habbitDto);

    ResponseData<List<Habbit>> list(HabbitDto habbitDto, Page<Habbit> page);
}
