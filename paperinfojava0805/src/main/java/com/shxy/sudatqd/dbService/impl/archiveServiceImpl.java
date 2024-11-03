package com.shxy.sudatqd.dbService.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shxy.models.dbBean.*;
import com.shxy.models.paramsBean.ID;
import com.shxy.models.paramsBean.archive.addArchivePaperParam;
import com.shxy.models.paramsBean.archive.addArchiveParam;
import com.shxy.models.paramsBean.archive.addRecordParam;
import com.shxy.models.paramsBean.archive.archiveQuery;
import com.shxy.models.paramsBean.idAndText;
import com.shxy.models.paramsBean.paper.paperQuery;
import com.shxy.models.views.archiveHome;
import com.shxy.models.views.archiveItem;
import com.shxy.models.views.operation.replyDTO;
import com.shxy.models.views.paperItem;
import com.shxy.sudatqd.dbService.*;
import com.shxy.sudatqd.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;

@Component
public class archiveServiceImpl extends ServiceImpl<archiveMapper, archive> implements archiveService {
    @Autowired
    private rolePermissionService rolePermissionService;
    @Autowired
    private pictureService pictureService;
    @Autowired
    private paperFiberService paperFiberService;
    @Autowired
    private archivePaperService archivePaperService;
    @Autowired
    private archiveComponentService archiveComponentService;
    @Autowired
    private auditService auditService;
    @Autowired
    private recordingMapper recordingMapper;
    @Autowired
    private archiveMapper archiveMapper;
    @Autowired
    private pictureMapper pictureMapper;
    @Override


    public PageInfo<archiveItem> archivePage(archiveQuery query) {
        // 使用PageHelper进行分页
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<archiveItem> archiveList = archiveMapper.selectArchiveItems(
                query.getArchiveName(),
                query.getInstitutionName(),
                query.getArchiveType(),
                query.getArchiveContent(),
                query.getCreator(),
                query.getState(),
                query.getOrderColumn(),
                query.getDatefilter(),
                query.getDatefilter1(),
                query.getIsAsc());
        // 获取分页后的结果
        PageInfo<archiveItem> pageInfo = new PageInfo<>(archiveList);
        return pageInfo;
    }

    public void addArchive(addArchiveParam archiveData, int oneId, DateTime now) {
        //分解请求参数
        List<MultipartFile> fileParam =archiveData.getFileParam();// 图片信息
        addRecordParam record = archiveData.getRecord();// 著录字段
        List<idAndText> archiveFiberList = archiveData.getArchiveFiberList();// 档案纸张纤维
        List<addArchivePaperParam> archivePaperList = archiveData.getArchivePaperList();//档案纸张信息
        List<ID> componentList = archiveData.getComponentList();//档案成分信息
        //根据权限需要变化的参数
        Integer archiveStateType = 0;
        //档案相关其他表记录的隐藏
        Integer status = 0;
        //获取用户权限
        List<Integer> pids = rolePermissionService.userPermission(oneId);
        if(pids.contains(5)){
            //具有档案管理权限，可直接添加档案
        }else{
            //普通用户上传，需提交审核
            archiveStateType = 1;
            status = 1;
        }
        //新增档案表信息
        archive newArchive = new archive();
        newArchive.setArchiveName(archiveData.getArchiveName());
        newArchive.setInstitutionId(archiveData.getInstitutionId());
        newArchive.setArchiveType(archiveData.getArchiveType());
        newArchive.setArchiveContent(archiveData.getArchiveContent());
        newArchive.setViews(0);
        newArchive.setArchiveStateType(archiveStateType);
        newArchive.setCreatorId(oneId);
        newArchive.setCreationTime(now);
        archiveMapper.insert(newArchive);
        Integer newId =newArchive.getArchiveId();

        //新增图片表信息，新增完成之后根据用户权限设置状态，0为正常，1为删除
        pictureService.addPicture(fileParam,0,newId,oneId);
        if(!pids.contains(5)){
            //普通用户
            UpdateWrapper<picture> picWrapper = new UpdateWrapper<>();
            picWrapper
                    .eq("objectType",0)
                    .eq("objectId", newId);
            picWrapper.set("state",1);
            pictureService.update(picWrapper);
        }
        //新增档案著录表信息
        recording newRecord = new recording();
        newRecord.setArchiveId(newId);
        newRecord.setBianHao(record.getBianHao());
        newRecord.setArchiveName(archiveData.getArchiveName());
        newRecord.setTime(record.getTime());
        newRecord.setAuthor(record.getAuthor());
        newRecord.setLanguage(record.getLanguage());
        newRecord.setClassification(record.getClassification());
        newRecord.setReference(record.getReference());
        newRecord.setCreatorId(oneId);
        newRecord.setCreationTime(now);
        newRecord.setStatus(status);
        recordingMapper.insert(newRecord);

        // 新增档案纸张纤维信息
        for (idAndText one : archiveFiberList) {
            paperFiberService.addPaperFiber(newId,one.getId(), Float.valueOf(one.getText()),oneId,status);
        }
        // 新增档案纸张信息
        for (addArchivePaperParam one : archivePaperList) {
            archivePaperService.addArchivePaper(
                    newId,one.getPaperId(),one.getPh(),one.getSize(),one.getThickness(),one.getWhiteness(),one.getColor(),one.getHengWen(),one.getBand(),one.getZongWen(),one.getZong(),one.getShuLiang(),
                    one.getNumbers(), one.getDistribution(), one.getLianWen(), one.getBeatDeg(), one.getDegFiber(), oneId, status
            );
        }
        //新增档案成分信息
        for (ID one : componentList) {
            archiveComponentService.addArchiveComponent(
                    newId,one.getId(),oneId,status
            );
        }
        // 新增审核表信息
        auditService.addAudit(0,newId);

    }

    public List<archiveHome> listHome(QueryWrapper<archiveHome> wrapper) {
        return archiveMapper.listHome(wrapper);
    }

}
