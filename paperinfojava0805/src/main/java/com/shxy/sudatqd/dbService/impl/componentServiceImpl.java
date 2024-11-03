package com.shxy.sudatqd.dbService.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shxy.models.dbBean.*;
import com.shxy.models.paramsBean.component.addComponentParam;
import com.shxy.models.paramsBean.component.componentQuery;
import com.shxy.models.views.componentItem;
import com.shxy.sudatqd.dbService.*;
import com.shxy.sudatqd.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class componentServiceImpl extends ServiceImpl<componentMapper, component> implements componentService {
    @Autowired
    private userService userService;
    @Autowired
    private componentMapper componentMapper;
    @Override
    public Integer addComponent(addComponentParam componentData, int oneId, DateTime now) {
        //新增纤维表信息
        component newComponent = new component();
        newComponent.setComponentName(componentData.getComponentName());
        newComponent.setComponentType(componentData.getComponentType());
        newComponent.setState(0);
        newComponent.setCreatorId(oneId);
        newComponent.setCreationTime(now);
        newComponent.setDescription(componentData.getDescription());
        componentMapper.insert(newComponent);
        return newComponent.getComponentId();
    }


    @Override
    public PageInfo<componentItem> componentPage(componentQuery query) {
        // 使用PageHelper进行分页
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<componentItem> componentList = componentMapper.selectComponentItems(
                query.getComponentName(),
                query.getComponentType(),
                query.getCreator(),
                query.getState(),
                query.getOrderColumn(),
                query.getIsAsc());
        // 获取分页后的结果
        PageInfo<componentItem> pageInfo = new PageInfo<>(componentList);
        return pageInfo;
    }

}
