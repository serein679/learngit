package com.shxy.sudatqd.dbService;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shxy.models.dbBean.comment;

import com.shxy.models.views.operation.replyDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface commentService extends IService<comment> {
    List<replyDTO> getCommentReplyList(Integer id, Integer type);


    void setChildren(replyDTO replyDTO);
}
