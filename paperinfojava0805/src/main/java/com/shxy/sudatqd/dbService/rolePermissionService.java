package com.shxy.sudatqd.dbService;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shxy.models.dbBean.permission;
import com.shxy.models.dbBean.role;
import com.shxy.models.dbBean.rolePermission;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface rolePermissionService extends IService<rolePermission> {
    void modifyRolePermissions(int roleId, List<Integer> permissionIds, int creatorId, DateTime now);

    List<Integer> userPermission (int userId);

    List<permission> getRolePermission (int id);

    List<role> getPermissionRole (int id);
}
