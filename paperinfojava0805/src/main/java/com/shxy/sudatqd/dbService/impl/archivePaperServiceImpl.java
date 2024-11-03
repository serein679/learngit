package com.shxy.sudatqd.dbService.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shxy.models.dbBean.*;
import com.shxy.models.views.archivePaperItem;
import com.shxy.models.views.paperItem;
import com.shxy.sudatqd.dbService.*;
import com.shxy.sudatqd.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class archivePaperServiceImpl extends ServiceImpl<archivePaperMapper, archivePaper> implements archivePaperService {

    @Autowired
    private archivePaperService archivePaperService;
    @Autowired
    private archivePaperMapper archivePaperMapper;

    @Override
    public void addArchivePaper(Integer archiveId,
                                Integer paperId,
                                String ph,
                                String size,
                                String thickness,
                                String whiteness,
                                String color,
                                Integer hengWen,
                                String band,
                                Integer zongWen,
                                String zong,
                                Integer shuLiang,
                                Integer numbers,
                                Integer distribution,
                                String lianWen,
                                String beatDeg,
                                String degFiber,
                                Integer creatorId,
                                Integer status) {

        archivePaper newItem = new archivePaper();
        newItem.setArchiveId(archiveId);
        newItem.setPaperId(paperId);
        newItem.setPh(ph);
        newItem.setSize(size);
        newItem.setThickness(thickness);
        newItem.setWhiteness(whiteness);
        newItem.setColor(color);
        newItem.setHengWen(hengWen);
        newItem.setBand(band);
        newItem.setZongWen(zongWen);
        newItem.setZong(zong);
        newItem.setShuLiang(shuLiang);
        newItem.setNumbers(numbers);
        newItem.setDistribution(distribution);
        newItem.setLianWen(lianWen);;
        newItem.setBeatDeg(beatDeg);
        newItem.setDegFiber(degFiber);
        newItem.setCreatorId(creatorId);
        newItem.setCreationTime(DateTime.now());
        newItem.setStatus(status);
        archivePaperMapper.insert(newItem);

    }

}
