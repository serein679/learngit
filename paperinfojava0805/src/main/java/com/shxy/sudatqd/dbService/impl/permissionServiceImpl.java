package com.shxy.sudatqd.dbService.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shxy.models.dbBean.*;
import com.shxy.models.paramsBean.user.permissionQuery;
import com.shxy.models.views.permissionItem;
import com.shxy.sudatqd.dbService.*;
import com.shxy.sudatqd.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class permissionServiceImpl extends ServiceImpl<permissionMapper, permission> implements permissionService {
    @Autowired
    private permissionService permissionService;
    @Autowired
    private userService userService;
    @Autowired
    private permissionMapper permissionMapper;
    @Autowired
    private userMapper userMapper;
    @Autowired
    private userRoleMapper userRoleMapper;
    @Autowired
    private rolePermissionMapper rolePermissionMapper;
    @Override
    public PageInfo<permissionItem> permissionPage(permissionQuery query) {
        // 使用PageHelper进行分页
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<permissionItem> permissionList = permissionMapper.selectPermissionItems(
                query.getPermissionName(),
                query.getDescription(),
                query.getCreator(),
                query.getState(),
                query.getOrderColumn(),
                query.getIsAsc());
        // 获取分页后的结果
        PageInfo<permissionItem> pageInfo = new PageInfo<>(permissionList);
        return pageInfo;
    }

    @Override
    public permissionItem getPermisisonItem(Integer id) {
        QueryWrapper<permission> list = new QueryWrapper<>();
        list.eq("permissionId",id);
        permission one=permissionService.getOne(list);
        QueryWrapper<user> u = new QueryWrapper<>();
        u.eq("userId",one.getCreatorId());
        String creatorName=userService.getOne(u).getUserName();
        permissionItem res= new permissionItem();
        res.setPermissionId(one.getPermissionId());
        res.setPermissionName(one.getPermissionName());
        res.setDescription(one.getDescription());
        res.setState(one.getState());
        res.setCreator(creatorName);
        res.setCreatorId(one.getCreatorId());
        res.setCreationTime(one.getCreationTime());
        return res;
    }

    @Override
    public List<user> getPermissionUser(Integer id){
        // 1. 根据 permissionId 查找权限的所有角色
        List<rolePermission> rps = rolePermissionMapper.selectList(new QueryWrapper<rolePermission>()
                .eq("permissionId", id));

        List<Integer> roleIds = rps.stream()
                .map(rolePermission::getRoleId)
                .collect(Collectors.toList());

        List<userRole> urs = userRoleMapper.selectList(new QueryWrapper<userRole>()
                .in("roleId", roleIds));

        List<user> res=new ArrayList<>();
        for (userRole u : urs) {
            user one = userMapper.selectOne(new QueryWrapper<user>()
                    .eq("userId", u.getUserId()));
            if(!res.contains(one)){
                res.add(one);
            }
        }
        return (res);
    }
}
