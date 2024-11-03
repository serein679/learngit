package com.shxy.sudatqd.dbService;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shxy.models.dbBean.permission;
import com.shxy.models.dbBean.role;
import com.shxy.models.dbBean.user;
import com.shxy.models.dbBean.userRole;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface userRoleService extends IService<userRole> {

    void modifyUserRoles(int userId, List<Integer> roleIds, int creatorId, DateTime now);
    List<role> getUserRole(int id);
    List<user> getRoleUser (int id);


}
