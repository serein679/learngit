package com.shxy.sudatqd.dbService.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shxy.models.dbBean.*;
import com.shxy.models.paramsBean.ID;
import com.shxy.models.paramsBean.archive.addArchivePaperParam;
import com.shxy.models.paramsBean.component.addComponentParam;
import com.shxy.models.paramsBean.fiber.fiberQuery;
import com.shxy.models.paramsBean.idAndText;
import com.shxy.models.paramsBean.operation.auditQuery;
import com.shxy.models.views.fiberItem;
import com.shxy.models.views.operation.auditItem;
import com.shxy.sudatqd.dbService.*;
import com.shxy.sudatqd.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class auditServiceImpl extends ServiceImpl<auditMapper, audit> implements auditService {
    @Autowired
    private userService userService;
    @Autowired
    private archiveService archiveService;
    @Autowired
    private reportCommentService reportCommentService;
    @Autowired
    private paperService paperService;
    @Autowired
    private auditService auditService;
    @Autowired
    private feedbackService feedbackService;
    @Autowired
    private pictureService pictureService;
    @Autowired
    private recordingService recordingService;
    @Autowired
    private paperFiberService paperFiberService;
    @Autowired
    private archivePaperService archivePaperService;
    @Autowired
    private archiveComponentService archiveComponentService;
    @Autowired
    private auditMapper auditMapper;
    @Autowired
    private pictureMapper pictureMapper;
    @Autowired
    private recordingMapper recordingMapper;
    @Autowired
    private paperFiberMapper paperFiberMapper;
    @Autowired
    private archivePaperMapper archivePaperMapper;
    @Autowired
    private archiveComponentMapper archiveComponentMapper;

    @Override
    public Integer addAudit(Integer type,
                            Integer targetId) {
        //新增审核表信息
        audit newItem = new audit();
        newItem.setTargetId(targetId);
        newItem.setType(type);
        newItem.setReviewComments(null);
        newItem.setState(0);
        newItem.setCreationTime(DateTime.now());
        auditMapper.insert(newItem);
        return newItem.getAuditId();
    }

    @Override
    public PageInfo<auditItem> auditPage(auditQuery query) {
        // 使用PageHelper进行分页
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        QueryWrapper<audit> queryWrapper = Wrappers.query(); // 使用Wrappers.query()创建QueryWrapper实例
        // 设置查询条件
        if(query.getType()!=null){
            queryWrapper.eq("type", query.getType());
        }
        if(query.getState()!=null){
            queryWrapper.eq("state", query.getState());
        }
        // 设置排序条件
        if (query.getIsAsc()) {
            queryWrapper.orderByAsc(query.getOrderColumn()); // 升序排序
        } else {
            queryWrapper.orderByDesc(query.getOrderColumn()); // 降序排序
        }
        // 执行查询
        List<audit> audits = auditMapper.selectList(queryWrapper);
        List<auditItem> auditList = new ArrayList<>();
        for (audit one : audits) {
            //userName
            String userName = "";
            if(one.getCreatorId()!=null){
                userService.getUserName(one.getCreatorId());
            }
            String targetName = "";
            //targetName:0档案、1纸张、2举报、3反馈
            if(one.getType()==0){
                //档案审核
                QueryWrapper<archive> aWrapper = Wrappers.query(); // 使用Wrappers.query()创建QueryWrapper实例
                // 设置查询条件
                aWrapper.eq("archiveId", one.getTargetId());
                targetName = archiveService.getOne(aWrapper).getArchiveName();
            } else if(one.getType()==1){
                //纸张审核
                QueryWrapper<paper> pWrapper = Wrappers.query(); // 使用Wrappers.query()创建QueryWrapper实例
                // 设置查询条件
                pWrapper.eq("paperId", one.getTargetId());
                targetName = paperService.getOne(pWrapper).getPaperName();
            } else if(one.getType()==2) {
                //举报审核
                QueryWrapper<reportComment> rWrapper = Wrappers.query(); // 使用Wrappers.query()创建QueryWrapper实例
                // 设置查询条件
                rWrapper.eq("reportId", one.getTargetId());
                targetName = reportCommentService.getOne(rWrapper).getContent();//投诉原因
            } else if (one.getType()==3){
                //反馈审核
                QueryWrapper<feedback> fWrapper = Wrappers.query(); // 使用Wrappers.query()创建QueryWrapper实例
                // 设置查询条件
                fWrapper.eq("feedbackId", one.getTargetId());
                targetName = feedbackService.getOne(fWrapper).getContent();//反馈原因
            }
            //构造返回结果
            auditItem item = new auditItem();
            item.setAuditId(one.getAuditId());
            item.setTargetId(one.getTargetId());
            item.setTargetName(targetName);
            item.setType(one.getType());
            item.setReviewComments(one.getReviewComments());
            item.setState(one.getState());
            item.setCreatorId(one.getCreatorId());
            item.setUserName(userName);
            item.setCreationTime(one.getCreationTime());
            auditList.add(item);
        }
        // 获取分页后的结果
        return new PageInfo<>(auditList);
    }

    @Override
    public void giveAudit(Integer auditId,Integer status,String content,Integer userId) {
        UpdateWrapper<audit> auditUpdateWrapper = new UpdateWrapper<>();
        auditUpdateWrapper.eq("auditId",auditId);
        //1通过，2不通过
        auditUpdateWrapper
                .set("state", status)
                .set("reviewComments",content)
                .set("creatorId",userId);
        auditService.update(auditUpdateWrapper);

        QueryWrapper<audit> queryWrapper = Wrappers.query();
        queryWrapper.eq("auditId", auditId);
        Integer type = auditService.getOne(queryWrapper).getType();
        Integer objectId = auditService.getOne(queryWrapper).getTargetId();
        // 0档案、1纸张、2举报、3反馈
        if(type==0){
            //档案1通过，修改档案表状态，将档案相关的其他表的status状态改为可见
            if(status==1){
                //档案表：archiveStateType=3
                UpdateWrapper<archive> archiveUW = new UpdateWrapper<>();
                archiveUW.eq("archiveId",objectId).set("archiveStateType", 3);
                archiveService.update(archiveUW);
                //图片表：state=0
                UpdateWrapper<picture> pictureUW = new UpdateWrapper<>();
                pictureUW.eq("objectType",0).eq("objectId",objectId).set("state", 0);
                pictureService.update(pictureUW);
                //档案著录表：status=0
                UpdateWrapper<recording> recordingUW = new UpdateWrapper<>();
                recordingUW.eq("archiveId",objectId).set("state", 0);
                recordingService.update(recordingUW);
                // 档案纸张纤维表：status=0
                UpdateWrapper<paperFiber> pfUW = new UpdateWrapper<>();
                pfUW.eq("archiveId",objectId).set("state", 0);
                paperFiberService.update(pfUW);
                // 档案纸张表：status=0
                UpdateWrapper<archivePaper> apUW = new UpdateWrapper<>();
                apUW.eq("archiveId",objectId).set("state", 0);
                archivePaperService.update(apUW);
                //档案成分信息：status=0
                UpdateWrapper<archiveComponent> acUW = new UpdateWrapper<>();
                acUW.eq("archiveId",objectId).set("state", 0);
                archiveComponentService.update(acUW);
            } else
            //档案2不通过，修改档案表状态，将档案相关的其他表的相关记录删除。
            if(status==2){
                // 档案表：archiveStateType=2
                UpdateWrapper<archive> archiveUW = new UpdateWrapper<>();
                archiveUW.eq("archiveId",objectId).set("archiveStateType", 2);
                archiveService.update(archiveUW);
                //图片表：state=0
                UpdateWrapper<picture> pictureUW = new UpdateWrapper<>();
                pictureUW.eq("objectType",0).eq("objectId",objectId);
                pictureMapper.delete(pictureUW);
                //档案著录表：status=0
                UpdateWrapper<recording> recordingUW = new UpdateWrapper<>();
                recordingUW.eq("archiveId",objectId);
                recordingMapper.delete(recordingUW);
                // 档案纸张纤维表：status=0
                UpdateWrapper<paperFiber> pfUW = new UpdateWrapper<>();
                pfUW.eq("archiveId",objectId);
                paperFiberMapper.delete(pfUW);
                // 档案纸张表：status=0
                UpdateWrapper<archivePaper> apUW = new UpdateWrapper<>();
                apUW.eq("archiveId",objectId);
                archivePaperMapper.delete(apUW);
                //档案成分信息：status=0
                UpdateWrapper<archiveComponent> acUW = new UpdateWrapper<>();
                acUW.eq("archiveId",objectId);
                archiveComponentMapper.delete(acUW);
            }
        } else
        if (type==1){
            //纸张//1通过，修改纸张状态state=3，将图片表状态改为可见
            if(status==1){
                //纸张表：state=3
                UpdateWrapper<paper> paperUW = new UpdateWrapper<>();
                paperUW.eq("paperId",objectId).set("state", 3);
                paperService.update(paperUW);
                //图片表：state=0
                UpdateWrapper<picture> pictureUW = new UpdateWrapper<>();
                pictureUW.eq("objectType",1).eq("objectId",objectId).set("state", 0);
                pictureService.update(pictureUW);
            } else
            //纸张//2不通过，修改纸张状态state=4，将删除图片表信息
            if(status==2){
                //纸张表：state=4
                UpdateWrapper<paper> paperUW = new UpdateWrapper<>();
                paperUW.eq("paperId",objectId).set("state", 4);
                paperService.update(paperUW);
                //图片表
                UpdateWrapper<picture> pictureUW = new UpdateWrapper<>();
                pictureUW.eq("objectType",1).eq("objectId",objectId);
                pictureMapper.delete(pictureUW);
            }
        } else
        if(type==2){
            //举报//1通过，修改reportComment表状态为1；//2不通过，修改reportComment表状态为2
            UpdateWrapper<reportComment> rcUW = new UpdateWrapper<>();
            rcUW.eq("reportId",objectId).set("reportState", status);
            reportCommentService.update(rcUW);
        } else
        if(type==3){
            //反馈//1通过，修改feedback表状态为1；//2不通过，修改feedbcak表状态为2
            UpdateWrapper<feedback> fUW = new UpdateWrapper<>();
            fUW.eq("feedbackId",objectId).set("state", status);
            feedbackService.update(fUW);
        }
    }
}
