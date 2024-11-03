package com.shxy.sudatqd.dbService.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shxy.models.dbBean.*;
import com.shxy.models.paramsBean.personalCenter.userCommentQuery;
import com.shxy.models.paramsBean.personalCenter.userComplainQuery;
import com.shxy.models.paramsBean.personalCenter.userFeedbackQuery;
import com.shxy.models.paramsBean.personalCenter.userReplyQuery;
import com.shxy.models.paramsBean.user.userQuery;
import com.shxy.models.views.operation.commentItem;
import com.shxy.models.views.operation.complainItem;
import com.shxy.models.views.operation.feedbackItem;
import com.shxy.models.views.operation.replyItem;
import com.shxy.sudatqd.dbService.*;
import com.shxy.sudatqd.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class userServiceImpl extends ServiceImpl<userMapper, user> implements userService {
    @Autowired
    private rolePermissionService rolePermissionService;
    @Autowired
    private permissionService permissionService;
    @Autowired
    private commentService commentService;
    @Autowired
    private replyService replyService;
    @Autowired
    private reportCommentService reportCommentService;
    @Autowired
    private feedbackService feedbackService;
    @Autowired
    private archiveService archiveService;
    @Autowired
    private paperService paperService;
    @Autowired
    private institutionService institutionService;
    @Autowired
    private fiberService fiberService;
    @Autowired
    private componentService componentService;
    @Autowired
    private userMapper userMapper;

    @Override
    public PageInfo<user> userPage(userQuery query) {
        // 使用PageHelper进行分页
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<user> userList = userMapper.selectUsers(
                query.getUserName(),
                query.getState(),
                query.getOrderColumn(),
                query.getIsAsc());
        // 获取分页后的结果
        PageInfo<user> pageInfo = new PageInfo<>(userList);
        return pageInfo;
    }
    @Override
    public String getUserName(Integer id) {
        QueryWrapper<user> queryWrapper = Wrappers.query(); // 使用Wrappers.query()创建QueryWrapper实例
        // 设置查询条件
        queryWrapper.eq("userId", id);
        return userMapper.selectOne(queryWrapper).getUserName();
    }
    @Override
    public PageInfo<commentItem> UserCommentList(Integer id, String name, userCommentQuery data) {
        // 使用PageHelper进行分页
        PageHelper.startPage(data.getPageNum(), data.getPageSize());
        //查询用户发表过的评论
        QueryWrapper<comment> commentWrapper = new QueryWrapper<>();
        commentWrapper.eq("userId",id);
        if(data.getObjectType()!=null){
            commentWrapper.eq("objectType",data.getObjectType());
        }
        if(data.getState()!=null){
            commentWrapper.eq("commentState",data.getState());
        }
        if(data.getContent()!=null){
            commentWrapper.like(StringUtils.hasText(data.getContent()),"commentContent",data.getContent());
        }
        if (data.getIsAsc()) {
            commentWrapper.orderByAsc(data.getOrderColumn());
        } else {
            commentWrapper.orderByDesc(data.getOrderColumn());
        }
        List<comment> comments = commentService.list(commentWrapper);
        //补充commentItem信息
        List<commentItem> res = new ArrayList<>();
        for (comment c : comments) {
            //获取评论对象的信息
            String objectName= "";
            Integer objectState =null;
            if(c.getObjectType()==1){
                //评论档案
                QueryWrapper<archive> archiveWrapper = new QueryWrapper<>();
                archiveWrapper.eq("archiveId",c.getObjectId());
                objectName =  archiveService.getOne(archiveWrapper).getArchiveName();
                objectState =  archiveService.getOne(archiveWrapper).getArchiveStateType();
            } else if (c.getObjectType()==2){
                //评论纸张
                QueryWrapper<paper> paperWrapper = new QueryWrapper<>();
                paperWrapper.eq("paperId",c.getObjectId());
                objectName =  paperService.getOne(paperWrapper).getPaperName();
                objectState =  paperService.getOne(paperWrapper).getState();
            } else if (c.getObjectType()==3){
                //评论机构
                QueryWrapper<institution> institutionWrapper = new QueryWrapper<>();
                institutionWrapper.eq("institutionId",c.getObjectId());
                objectName =  institutionService.getOne(institutionWrapper).getInstitutionName();
                objectState =  institutionService.getOne(institutionWrapper).getState();
            } else if (c.getObjectType()==4){
                //评论纤维
                QueryWrapper<fiber> fiberWrapper = new QueryWrapper<>();
                fiberWrapper.eq("fiberId",c.getObjectId());
                objectName =  fiberService.getOne(fiberWrapper).getFiberType();
                objectState =  fiberService.getOne(fiberWrapper).getState();
            } else if (c.getObjectType()==5){
                //评论成分
                QueryWrapper<component> componentWrapper = new QueryWrapper<>();
                componentWrapper.eq("componentId",c.getObjectId());
                objectName =  componentService.getOne(componentWrapper).getComponentName();
                objectState =  componentService.getOne(componentWrapper).getState();
            }
            //补充一条记录
            commentItem item = new commentItem();
            item.setCommentId(c.getCommentId());
            item.setUserId(c.getUserId());
            item.setUserName(name);
            item.setObjectType(c.getObjectType());
            item.setObjectId(c.getObjectId());
            item.setObjectName(objectName);
            item.setObjectState(objectState);
            item.setCommentState(c.getCommentState());
            item.setCommentTime(c.getCommentTime());
            item.setCommentContent(c.getCommentContent());
            //将记录加到结果列表中
            res.add(item);
        }
        // 获取分页后的结果
        PageInfo<commentItem> pageInfo = new PageInfo<>(res);
        return pageInfo;
    }

    @Override
    public PageInfo<replyItem> UserReplyList(Integer id, String name, userReplyQuery data) {
        // 使用PageHelper进行分页
        PageHelper.startPage(data.getPageNum(), data.getPageSize());
        //查询用户发表过的回复
        QueryWrapper<reply> replyWrapper = new QueryWrapper<>();
        replyWrapper.eq("userId",id);
        if(data.getObjectType()!=null){
            replyWrapper.eq("type",data.getObjectType());
        }
        if(data.getState()!=null){
            replyWrapper.eq("replyState",data.getState());
        }
        if(data.getContent()!=null){
            replyWrapper.like("replyContent",data.getContent());
        }
        if (data.getIsAsc()) {
           replyWrapper.orderByAsc(data.getOrderColumn());
        } else {
           replyWrapper.orderByDesc(data.getOrderColumn());
        }
        List<reply> replys = replyService.list(replyWrapper);
        //补充replyItem信息
        List<replyItem> res = new ArrayList<>();
        for (reply r : replys) {
            //获取评论对象的信息
            String objectContent= "";
            Integer objectState =null;
            if(r.getType()==0){
                //回复评论
                QueryWrapper<comment> commentWrapper = new QueryWrapper<>();
                commentWrapper.eq("commentId",r.getReplyyId());
                objectContent =  commentService.getOne(commentWrapper).getCommentContent();
                objectState =  commentService.getOne(commentWrapper).getCommentState();
            } else if (r.getType()==1){
                //回复回复
                QueryWrapper<reply> replyyWrapper = new QueryWrapper<>();
                replyyWrapper.eq("replyId",r.getReplyyId());
                objectContent =  replyService.getOne(replyyWrapper).getReplyContent();
                objectState =  replyService.getOne(replyyWrapper).getReplyState();
            }
            //补充一条记录
            replyItem item = new replyItem();
            item.setReplyId(r.getReplyId());
            item.setUserId(r.getUserId());
            item.setUserName(name);
            item.setReplyyId(r.getReplyyId());
            item.setObjectContent(objectContent);
            item.setObjectState(objectState);
            item.setType(r.getType());
            item.setReplyContent(r.getReplyContent());
            item.setReplyTime(r.getReplyTime());
            item.setReplyState(r.getReplyState());
            //将记录加到结果列表中
            res.add(item);
        }
        // 获取分页后的结果
        PageInfo<replyItem> pageInfo = new PageInfo<>(res);
        return pageInfo;
    }
    @Override
    public PageInfo<complainItem> UserComplainList(Integer id, String name, userComplainQuery data) {
        // 使用PageHelper进行分页
        PageHelper.startPage(data.getPageNum(), data.getPageSize());
        //查询用户发表过的举报
        QueryWrapper<reportComment> complainWrapper = new QueryWrapper<>();
        complainWrapper.eq("reporterId",id);
        if (data.getObjectType() != null) {
            complainWrapper.eq("reportReasonType", data.getObjectType());
        }

        if (data.getState() != null) {
            complainWrapper.eq("reportState", data.getState());
        }

        if (data.getContentType() != null) {
            complainWrapper.eq("reportContentType", data.getContentType());
        }

        if (StringUtils.hasText(data.getContent())) {
            complainWrapper.like("content", data.getContent());
        }
        if (data.getIsAsc()) {
           complainWrapper.orderByAsc(data.getOrderColumn());
        } else {
            complainWrapper.orderByDesc(data.getOrderColumn());
        }
        List<reportComment> complains = reportCommentService.list(complainWrapper);
        //补充complainItem信息
        List<complainItem> res = new ArrayList<>();
        for (reportComment r : complains) {
            //获取评论对象的信息
            String objectContent= "";
            Integer objectState =null;
            if(r.getReportReasonType()==0){
                //举报评论
                QueryWrapper<comment> commentWrapper = new QueryWrapper<>();
                commentWrapper.eq("commentId",r.getCommentId());
                objectContent =  commentService.getOne(commentWrapper).getCommentContent();
                objectState =  commentService.getOne(commentWrapper).getCommentState();
            } else if (r.getReportReasonType()==1){
                //举报回复
                QueryWrapper<reply> replyyWrapper = new QueryWrapper<>();
                replyyWrapper.eq("replyId",r.getCommentId());
                objectContent =  replyService.getOne(replyyWrapper).getReplyContent();
                objectState =  replyService.getOne(replyyWrapper).getReplyState();
            }
            //补充一条记录
            complainItem item = new complainItem();
            item.setReportId(r.getReportId());
            item.setCommentId(r.getCommentId());
            item.setReportReasonType(r.getReportReasonType());
            item.setObjectContent(objectContent);
            item.setObjectState(objectState);
            item.setReporterId(r.getReporterId());
            item.setUserName(name);
            item.setReportContentType(r.getReportContentType());
            item.setContent(r.getContent());
            item.setReportTime(r.getReportTime());
            item.setReportState(r.getReportState());
            //将记录加到结果列表中
            res.add(item);
        }
        // 获取分页后的结果
        PageInfo<complainItem> pageInfo = new PageInfo<>(res);
        return pageInfo;
    }

    @Override
    public PageInfo<feedbackItem> UserFeedbackList(Integer id, String name, userFeedbackQuery data) {
        // 使用PageHelper进行分页
        PageHelper.startPage(data.getPageNum(), data.getPageSize());
        //查询用户发表过的回复
        QueryWrapper<feedback> feedbackWrapper = new QueryWrapper<>();
        feedbackWrapper.eq("userId",id);
        if (data.getObjectType() != null) {
            feedbackWrapper.eq("feedbackType", data.getObjectType());
        }

        if (data.getState() != null) {
            feedbackWrapper.eq("state", data.getState());
        }

        if (StringUtils.hasText(data.getContent())) {
            feedbackWrapper.like("content", data.getContent());
        }
        if (data.getIsAsc()) {
            feedbackWrapper.orderByAsc(data.getOrderColumn());
        } else {
            feedbackWrapper.orderByDesc(data.getOrderColumn());
        }
        List<feedback> feedbacks = feedbackService.list(feedbackWrapper);
        //补充feedbackItem信息
        List<feedbackItem> res = new ArrayList<>();
        for (feedback f : feedbacks) {
            //获取评论对象的信息
            String objectName= "";
            Integer objectState =null;
            if(f.getFeedbackType()==1){
                //评论档案
                QueryWrapper<archive> archiveWrapper = new QueryWrapper<>();
                archiveWrapper.eq("archiveId",f.getObjectId());
                objectName =  archiveService.getOne(archiveWrapper).getArchiveName();
                objectState =  archiveService.getOne(archiveWrapper).getArchiveStateType();
            } else if (f.getFeedbackType()==2){
                //评论纸张
                QueryWrapper<paper> paperWrapper = new QueryWrapper<>();
                paperWrapper.eq("paperId",f.getObjectId());
                objectName =  paperService.getOne(paperWrapper).getPaperName();
                objectState =  paperService.getOne(paperWrapper).getState();
            } else if (f.getFeedbackType()==3){
                //评论机构
                QueryWrapper<institution> institutionWrapper = new QueryWrapper<>();
                institutionWrapper.eq("institutionId",f.getObjectId());
                objectName =  institutionService.getOne(institutionWrapper).getInstitutionName();
                objectState =  institutionService.getOne(institutionWrapper).getState();
            } else if (f.getFeedbackType()==4){
                //评论纤维
                QueryWrapper<fiber> fiberWrapper = new QueryWrapper<>();
                fiberWrapper.eq("fiberId",f.getObjectId());
                objectName =  fiberService.getOne(fiberWrapper).getFiberType();
                objectState =  fiberService.getOne(fiberWrapper).getState();
            } else if (f.getFeedbackType()==5){
                //评论成分
                QueryWrapper<component> componentWrapper = new QueryWrapper<>();
                componentWrapper.eq("componentId",f.getObjectId());
                objectName =  componentService.getOne(componentWrapper).getComponentName();
                objectState =  componentService.getOne(componentWrapper).getState();
            }
            //补充一条记录
            feedbackItem item = new  feedbackItem();
            item.setFeedbackId(f.getFeedbackId());
            item.setUserId(f.getUserId());
            item.setUserName(name);
            item.setObjectId(f.getObjectId());
            item.setFeedbackType(f.getFeedbackType());
            item.setObjectName(objectName);
            item.setObjectState(objectState);
            item.setContent(f.getContent());
            item.setCreationTime(f.getCreationTime());
            item.setState(f.getState());
            //将记录加到结果列表中
            res.add(item);
        }
        // 获取分页后的结果
        PageInfo<feedbackItem> pageInfo = new PageInfo<>(res);
        return pageInfo;
    }

    @Override
    public List<permission> getUserPermissionList(int id) {
        //获取用户所有的权限id
        List<Integer> pIds = rolePermissionService.userPermission(id);
        List<permission> permissionList = new ArrayList<>();
        for (Integer pid : pIds) {
            QueryWrapper<permission> rpWrapper = new QueryWrapper<>();
            rpWrapper.eq("permissionId",pid);
            permission p = permissionService.getOne(rpWrapper);
            permissionList.add(p);
        }
        return permissionList;
    }
}
