package com.shxy.sudatqd.dbService.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shxy.models.dbBean.*;
import com.shxy.sudatqd.dbService.*;
import com.shxy.sudatqd.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class archiveComponentServiceImpl extends ServiceImpl<archiveComponentMapper, archiveComponent> implements archiveComponentService {


    @Autowired
    private archiveComponentMapper archiveComponentMapper;

    @Override
    public void addArchiveComponent(Integer archiveId,
                                Integer componentId,
                                Integer creatorId,
                                Integer status) {

        archiveComponent newItem = new archiveComponent();
        newItem.setArchiveId(archiveId);
        newItem.setComponentId(componentId);
        newItem.setCreatorId(creatorId);
        newItem.setCreationTime(DateTime.now());
        newItem.setStatus(status);
        archiveComponentMapper.insert(newItem);

    }
}
