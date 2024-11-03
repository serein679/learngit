package com.shxy.sudatqd.dbService.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shxy.models.dbBean.*;
import com.shxy.models.paramsBean.*;
import com.shxy.models.paramsBean.component.componentQuery;
import com.shxy.models.paramsBean.fiber.addFiberParam;
import com.shxy.models.paramsBean.fiber.fiberQuery;
import com.shxy.models.views.componentItem;
import com.shxy.models.views.fiberItem;
import com.shxy.sudatqd.dbService.*;
import com.shxy.sudatqd.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class fiberServiceImpl extends ServiceImpl<fiberMapper, fiber> implements fiberService {
    @Autowired
    private fiberMapper fiberMapper;

    @Override
    public Integer addFiber(addFiberParam fiberData, int oneId, DateTime now) {
        //新增纤维表信息
        fiber newFiber = new fiber();
        newFiber.setFiberType(fiberData.getFiberType());
        newFiber.setCharacteristic(fiberData.getCharacteristic());
        newFiber.setState(0);
        newFiber.setViews(0);
        newFiber.setCreatorId(oneId);
        newFiber.setCreationTime(now);
        fiberMapper.insert(newFiber);
        return newFiber.getFiberId();
    }

    @Override
    public PageInfo<fiberItem> fiberPage(fiberQuery query) {
        // 使用PageHelper进行分页
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<fiberItem> fiberList = fiberMapper.selectFiberItems(
                query.getFiberType(),
                query.getCharacteristic(),
                query.getCreator(),
                query.getState(),
                query.getOrderColumn(),
                query.getIsAsc());
        // 获取分页后的结果
        PageInfo<fiberItem> pageInfo = new PageInfo<>(fiberList);
        return pageInfo;
    }


}
