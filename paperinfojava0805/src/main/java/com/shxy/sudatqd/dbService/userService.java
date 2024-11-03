package com.shxy.sudatqd.dbService;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.shxy.models.dbBean.permission;
import com.shxy.models.dbBean.user;
import com.shxy.models.paramsBean.personalCenter.userCommentQuery;
import com.shxy.models.paramsBean.personalCenter.userComplainQuery;
import com.shxy.models.paramsBean.personalCenter.userFeedbackQuery;
import com.shxy.models.paramsBean.personalCenter.userReplyQuery;
import com.shxy.models.paramsBean.user.userQuery;
import com.shxy.models.views.operation.*;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface userService extends IService<user> {
    PageInfo<user> userPage(userQuery userQuery);
    String getUserName(Integer id);
    PageInfo<commentItem> UserCommentList(Integer id, String name, userCommentQuery data);
    PageInfo<replyItem> UserReplyList(Integer id, String name, userReplyQuery data);
    PageInfo<complainItem> UserComplainList(Integer id, String name, userComplainQuery data);
    PageInfo<feedbackItem> UserFeedbackList(Integer id, String name, userFeedbackQuery data);

    List<permission> getUserPermissionList(int id);

}
