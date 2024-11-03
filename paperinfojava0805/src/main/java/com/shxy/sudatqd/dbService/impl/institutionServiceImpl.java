package com.shxy.sudatqd.dbService.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shxy.models.dbBean.*;
import com.shxy.models.paramsBean.institution.addInstitutionParam;
import com.shxy.models.paramsBean.institution.institutionQuery;
import com.shxy.models.paramsBean.paper.addPaperParam;
import com.shxy.models.paramsBean.word.wordQuery;
import com.shxy.models.views.archiveHome;
import com.shxy.models.views.institutionItem;
import com.shxy.models.views.wordItem;
import com.shxy.sudatqd.dbService.*;
import com.shxy.sudatqd.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class institutionServiceImpl extends ServiceImpl<institutionMapper, institution> implements institutionService {
    @Autowired
    private institutionMapper institutionMapper;

    @Override
    public Integer addInstitution(addInstitutionParam institutionData, int oneId, DateTime now) {
        //新增机构表信息
        institution newInstitution = new institution();
        newInstitution.setInstitutionName(institutionData.getInstitutionName());
        newInstitution.setInstitutionLocation(institutionData.getInstitutionLocation());
        newInstitution.setLongitude(institutionData.getLongitude());
        newInstitution.setLatitude(institutionData.getLatitude());
        newInstitution.setInstitutionDate(institutionData.getInstitutionDate());
        newInstitution.setInstitutionDescription(institutionData.getInstitutionDescription());
        newInstitution.setState(0);
        newInstitution.setViews(0);
        newInstitution.setCreatorId(oneId);
        newInstitution.setCreationTime(now);
        institutionMapper.insert(newInstitution);
        return newInstitution.getInstitutionId();
    }

    @Override
    public PageInfo<institutionItem> institutionPage(institutionQuery query) {
        // 使用PageHelper进行分页
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<institutionItem> institutionList = institutionMapper.selectInstitutionItems(
                query.getInstitutionName(),
                query.getInstitutionLocation(),
                query.getStartDate(),
                query.getEndDate(),
                query.getInstitutionDescription(),
                query.getCreator(),
                query.getState(),
                query.getOrderColumn(),
                query.getIsAsc());
        // 获取分页后的结果
        PageInfo<institutionItem> pageInfo = new PageInfo<>(institutionList);
        return pageInfo;
    }

    public List<institutionItem> listHome(QueryWrapper<institutionItem> wrapper) {
        return institutionMapper.listHome(wrapper);
    }
}
