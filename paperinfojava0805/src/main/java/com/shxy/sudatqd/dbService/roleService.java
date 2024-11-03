package com.shxy.sudatqd.dbService;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.shxy.models.dbBean.role;

import com.shxy.models.paramsBean.paper.paperQuery;
import com.shxy.models.paramsBean.user.addRoleParam;
import com.shxy.models.paramsBean.user.roleQuery;
import com.shxy.models.views.paperItem;
import com.shxy.models.views.roleItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface roleService extends IService<role> {
    void addRole(addRoleParam data, int oneId, DateTime now);
    PageInfo<roleItem> rolePage(roleQuery roleQuery);
    roleItem getRoleItem(Integer id);
}
