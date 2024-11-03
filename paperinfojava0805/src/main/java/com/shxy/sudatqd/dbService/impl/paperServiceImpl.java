package com.shxy.sudatqd.dbService.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shxy.models.dbBean.*;
import com.shxy.models.paramsBean.fiber.fiberQuery;
import com.shxy.models.paramsBean.paper.addPaperParam;
import com.shxy.models.paramsBean.paper.paperQuery;
import com.shxy.models.views.archiveHome;
import com.shxy.models.views.fiberItem;
import com.shxy.models.views.paperItem;
import com.shxy.models.views.roleItem;
import com.shxy.sudatqd.dbService.*;
import com.shxy.sudatqd.mapper.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class paperServiceImpl extends ServiceImpl<paperMapper, paper> implements paperService {
    @Autowired
    private userService userService;
    @Autowired
    private archiveService archiveService;
    @Autowired
    private archivePaperService archivePaperService;
    @Autowired
    private archivePaperMapper archivePaperMapper;
    @Autowired
    private paperMapper paperMapper;

    @Override
    public Integer addPaper(addPaperParam paperData, int oneId, DateTime now) {
        //新增纸张表信息
        paper newPaper = new paper();
        newPaper.setPaperName(paperData.getPaperName());
        newPaper.setPaperDes(paperData.getPaperDes());
        newPaper.setPaperType(paperData.getPaperType());
        newPaper.setState(0);
        newPaper.setLocation(paperData.getLocation());
        newPaper.setProcess(paperData.getProcess());
        newPaper.setTechnology(paperData.getTechnology());
        newPaper.setViews(0);
        newPaper.setCreatorId(oneId);
        newPaper.setCreationTime(now);
        paperMapper.insert(newPaper);
//        Integer paperId = newPaper.getPaperId();
//        List<Integer> archiveIds =paperData.getArchiveIds();
//        int size1 = archiveIds.size();
//        System.out.println(archiveIds);
//        if (size1 > 0) {
//            List<archivePaper> archiveIdList = new ArrayList<>(size1);
//            for (int i = 0; i < archiveIds.size(); i++) {
//                System.out.println(archiveIds.get(i));
//                Integer cou = archiveIds.get(i);
//                UpdateWrapper<archivePaper> usersUpdateWrapper = new UpdateWrapper<>();
//                usersUpdateWrapper.eq("archiveId", cou);
//                usersUpdateWrapper.set("paperId", paperId);
//               archivePaperService.update(usersUpdateWrapper);
//            }
//            System.out.println(archiveIdList);
//            archivePaperService.saveBatch(archiveIdList);
//            System.out.println(archiveIdList);
//        }
        return newPaper.getPaperId();
    }

    @Override
    public PageInfo<paperItem> paperPage(paperQuery query) {
        // 使用PageHelper进行分页
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<paperItem> paperList = paperMapper.selectPaperItems(
                query.getPaperName(),
                query.getPaperDes(),
                query.getPaperType(),
                query.getLocation(),
                query.getProcess(),
                query.getTechnology(),
                query.getCreator(),
                query.getState(),
                query.getOrderColumn(),
                query.getIsAsc());
        // 获取分页后的结果
        PageInfo<paperItem> pageInfo = new PageInfo<>(paperList);
        return pageInfo;
    }
    public List<paperItem> listHome(QueryWrapper<paperItem> wrapper) {
        return paperMapper.listHome(wrapper);
    }
}
