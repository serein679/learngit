package com.shxy.sudatqd.dbService;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.shxy.models.dbBean.institution;

import com.shxy.models.paramsBean.institution.addInstitutionParam;
import com.shxy.models.paramsBean.institution.institutionQuery;
import com.shxy.models.paramsBean.paper.addPaperParam;
import com.shxy.models.paramsBean.word.wordQuery;
import com.shxy.models.views.archiveHome;
import com.shxy.models.views.institutionItem;
import com.shxy.models.views.wordItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface institutionService extends IService<institution> {
    Integer addInstitution(addInstitutionParam institutionData, int oneId, DateTime now);
    PageInfo<institutionItem> institutionPage(institutionQuery institutionQuery);
    List<institutionItem> listHome(QueryWrapper<institutionItem> wrapper);
}
