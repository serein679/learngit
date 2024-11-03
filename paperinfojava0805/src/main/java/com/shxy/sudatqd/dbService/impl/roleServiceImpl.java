package com.shxy.sudatqd.dbService.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shxy.models.dbBean.*;
import com.shxy.models.paramsBean.paper.paperQuery;
import com.shxy.models.paramsBean.user.addRoleParam;
import com.shxy.models.paramsBean.user.roleQuery;
import com.shxy.models.views.paperItem;
import com.shxy.models.views.roleItem;
import com.shxy.sudatqd.dbService.*;
import com.shxy.sudatqd.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class roleServiceImpl extends ServiceImpl<roleMapper, role> implements roleService {
    @Autowired
    private userService userService;
    @Autowired
    private roleService roleService;
    @Autowired
    private roleMapper roleMapper;
    @Autowired
    private rolePermissionMapper rolePermissionMapper;

    @Override
    public void addRole(addRoleParam data, int oneId, DateTime now) {
        //新增角色表信息
        role newRole = new role();
        newRole.setRoleName(data.getRoleName());
        newRole.setDescription(data.getDescription());
        newRole.setCreatorId(oneId);
        newRole.setCreationTime(now);
        newRole.setState(1);
        roleMapper.insert(newRole);
        //新增角色权限表信息
        for (Integer permissionId : data.getPermissionList()) {
            rolePermission map = new rolePermission();
            map.setRoleId(newRole.getRoleId());
            map.setPermissionId(permissionId);
            map.setCreatorId(oneId);
            map.setCreationTime(now);
            rolePermissionMapper.insert(map);
        }
    }

    @Override
    public PageInfo<roleItem> rolePage(roleQuery query) {
        // 使用PageHelper进行分页
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<roleItem> roleList = roleMapper.selectRoleItems(
                query.getRoleName(),
                query.getDescription(),
                query.getCreator(),
                query.getState(),
                query.getOrderColumn(),
                query.getIsAsc());
        // 获取分页后的结果
        PageInfo<roleItem> pageInfo = new PageInfo<>(roleList);
        return pageInfo;
    }

    @Override
    public roleItem getRoleItem(Integer id) {
        QueryWrapper<role> list = new QueryWrapper<>();
        list.eq("roleId",id);
        role one=roleService.getOne(list);
        QueryWrapper<user> u = new QueryWrapper<>();
        u.eq("userId",one.getCreatorId());
        String creatorName=userService.getOne(u).getUserName();
        roleItem res= new roleItem();
        res.setRoleId(one.getRoleId());;
        res.setRoleName(one.getRoleName());
        res.setDescription(one.getDescription());
        res.setState(one.getState());
        res.setCreator(creatorName);
        res.setCreatorId(one.getCreatorId());
        res.setCreationTime(one.getCreationTime());
        return res;

    }


}
