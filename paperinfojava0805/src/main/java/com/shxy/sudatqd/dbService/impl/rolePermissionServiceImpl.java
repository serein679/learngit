package com.shxy.sudatqd.dbService.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shxy.models.dbBean.*;
import com.shxy.sudatqd.dbService.*;
import com.shxy.sudatqd.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class rolePermissionServiceImpl extends ServiceImpl<rolePermissionMapper, rolePermission> implements rolePermissionService {
    @Autowired
    private userRoleMapper userRoleMapper;
    @Autowired
    private roleMapper roleMapper;
    @Autowired
    private rolePermissionMapper rolePermissionMapper;
    @Autowired
    private permissionMapper permissionMapper;
    @Override
    public void modifyRolePermissions(int roleId, List<Integer> permissionIds, int creatorId, DateTime now) {
        UpdateWrapper<rolePermission> wrapper = new UpdateWrapper<>();
        wrapper.eq("roleId", roleId);
        rolePermissionMapper.delete(wrapper);
        for (Integer permissionId : permissionIds) {
            rolePermission map = new rolePermission();
            map.setRoleId(roleId);
            map.setPermissionId(permissionId);
            map.setCreatorId(creatorId);
            map.setCreationTime(DateTime.now());
            rolePermissionMapper.insert(map);
        }
    }

    public List<Integer> userPermission(int userId){
        // 1. 根据 userId 查找用户拥有的角色
        List<userRole> userRoles = userRoleMapper.selectList(new QueryWrapper<userRole>()
                .eq("userId", userId));
        List<Integer> roleIds = userRoles.stream()
                .map(userRole::getRoleId)
                .collect(Collectors.toList());

        List<rolePermission> rolePermissions = rolePermissionMapper.selectList(new QueryWrapper<rolePermission>()
                .in("roleId", roleIds));

        Set<Integer> permissionIds = new HashSet<>();
        for (rolePermission rp : rolePermissions) {
            permissionIds.add(rp.getPermissionId());
        }
        List<Integer> permissionIdsList = new ArrayList<>(permissionIds);
        return (permissionIdsList);
    }
    public List<permission> getRolePermission (int id){
        // 1. 根据 roleId 查找角色的所有权限
        List<rolePermission> rps = rolePermissionMapper.selectList(new QueryWrapper<rolePermission>()
                .eq("roleId", id));

        List<permission> res=new ArrayList<>();
        for (rolePermission rp : rps) {
            permission ps = permissionMapper.selectOne(new QueryWrapper<permission>()
                    .eq("permissionId", rp.getPermissionId()));
            res.add(ps);
        }
        return (res);
    }

    public List<role> getPermissionRole (int id){
        // 1. 根据 permissionId 查找权限的所有角色
        List<rolePermission> rps = rolePermissionMapper.selectList(new QueryWrapper<rolePermission>()
                .eq("permissionId", id));

        List<role> res=new ArrayList<>();
        for (rolePermission rp : rps) {
            role ps = roleMapper.selectOne(new QueryWrapper<role>()
                    .eq("roleId", rp.getRoleId()));
            res.add(ps);
        }
        return (res);
    }
}
