package com.shxy.sudatqd.dbService.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shxy.models.dbBean.*;
import com.shxy.models.paramsBean.institution.addInstitutionParam;
import com.shxy.sudatqd.dbService.*;
import com.shxy.sudatqd.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class paperFiberServiceImpl extends ServiceImpl<paperFiberMapper, paperFiber> implements paperFiberService {
    @Autowired
    private paperFiberService paperFiberService;
    @Autowired
    private paperFiberMapper paperFiberMapper;

    @Override
    public void addPaperFiber(Integer archiveId,
                                 Integer fiberId,
                                 Float fiberContent,
                                 Integer creatorId,
                                 Integer status) {

        paperFiber newItem = new paperFiber();
        newItem.setPaperId(archiveId);
        newItem.setFiberId(fiberId);
        newItem.setFiberContent(fiberContent);
        newItem.setCreatorId(creatorId);
        newItem.setCreationTime(DateTime.now());
        newItem.setStatus(status);
        paperFiberMapper.insert(newItem);

    }
}
