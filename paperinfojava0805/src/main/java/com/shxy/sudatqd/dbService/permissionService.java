package com.shxy.sudatqd.dbService;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.shxy.models.dbBean.permission;

import com.shxy.models.dbBean.user;
import com.shxy.models.paramsBean.user.permissionQuery;
import com.shxy.models.paramsBean.user.roleQuery;
import com.shxy.models.views.permissionItem;
import com.shxy.models.views.roleItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface permissionService extends IService<permission> {
    PageInfo<permissionItem> permissionPage(permissionQuery permissionQuery);

    permissionItem getPermisisonItem(Integer id);

    List<user> getPermissionUser(Integer id);

}
