package com.shxy.sudatqd.dbService.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shxy.models.dbBean.*;
import com.shxy.models.views.operation.replyDTO;
import com.shxy.models.views.operation.replyItem;
import com.shxy.sudatqd.dbService.*;
import com.shxy.sudatqd.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class commentServiceImpl extends ServiceImpl<commentMapper, comment> implements commentService {
    @Autowired
    private userService userService;
    @Autowired
    private replyService replyService;
    @Autowired
    private commentMapper commentMapper;
    @Autowired
    private replyMapper replyMapper;
    @Override
    public List<replyDTO> getCommentReplyList(Integer id,Integer type) {

        //获取该对象的评论
        List<comment> comments = commentMapper.selectList(new QueryWrapper<comment>()
                .eq("objectId", id)
                .eq("objectType", type)
                .eq("commentState", 0));

        List<Integer> cIds = comments.stream()
                .map(comment::getCommentId)
                .collect(Collectors.toList());

        //获取对评论的回复
        List<reply> mainReplys = replyMapper.selectList(new QueryWrapper<reply>()
                .in("replyyId", cIds)
                .eq("type",0)
                .eq("replyState",1));
        return getAllComments(mainReplys);
    }


    public List<replyDTO> getAllComments(List<reply> mainReplys) {
        List<replyDTO> rootComments = getCommentReplyTrees(mainReplys);
//        List<replyDTO> rootComments = new ArrayList<>();
//        for (reply r : mainReplys) {
//            QueryWrapper<user> list = new QueryWrapper<>();
//            list.eq("userId",r.getUserId());
//            String oneName=userService.getOne(list).getUserName();
//
//            replyDTO one =new replyDTO();
//            one.setReplyId(r.getReplyId());
//            one.setUserId(r.getUserId());
//            one.setReplyyId(r.getReplyyId());
//            one.setType(r.getType());
//            one.setReplyContent(r.getReplyContent());
//            one.setReplyTime(r.getReplyTime());
//            one.setReplyState(r.getReplyState());
//            one.setUserName(oneName);
//            rootComments.add(one);
//        }

        rootComments.forEach(this::setChildren);
        return rootComments;
    }

    /**
     * 递归获取
     * @param replyDTO 参数
     */
    @Override
    public void setChildren(replyDTO replyDTO){
        QueryWrapper<reply> list = new QueryWrapper<>();
        list.eq("replyyId",replyDTO.getReplyId())
                .eq("type",1)
                 .eq("replyState",1);
        List<reply> replys=replyService.list(list);
        List<replyDTO> children = getCommentReplyTrees(replys);
//        List<replyDTO> children = new ArrayList<>();
//        for (reply r : replys) {
//            QueryWrapper<user> ulist = new QueryWrapper<>();
//            ulist.eq("userId",r.getUserId());
//            String oneName=userService.getOne(ulist).getUserName();
//
//            replyDTO one =new replyDTO();
//            one.setReplyId(r.getReplyId());
//            one.setUserId(r.getUserId());
//            one.setReplyyId(r.getReplyyId());
//            one.setType(r.getType());
//            one.setReplyContent(r.getReplyContent());
//            one.setReplyTime(r.getReplyTime());
//            one.setReplyState(r.getReplyState());
//            one.setUserName(oneName);
//            children.add(one);
//        }

        if (!children.isEmpty()) {
            replyDTO.setChildren(children);
            children.forEach(this::setChildren);
        }
    }

    public List<replyDTO> getCommentReplyTrees(List<reply> replys){
        List<replyDTO> res = new ArrayList<>();
        for (reply r : replys) {
            QueryWrapper<user> ulist = new QueryWrapper<>();
            ulist.eq("userId",r.getUserId());
            String oneName=userService.getOne(ulist).getUserName();

            replyDTO one =new replyDTO();
            one.setReplyId(r.getReplyId());
            one.setUserId(r.getUserId());
            one.setReplyyId(r.getReplyyId());
            one.setType(r.getType());
            one.setReplyContent(r.getReplyContent());
            one.setReplyTime(r.getReplyTime());
            one.setReplyState(r.getReplyState());
            one.setUserName(oneName);
            res.add(one);
        }
        return res;
    }

}
