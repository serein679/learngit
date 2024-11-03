package com.shxy.sudatqd.dbService.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shxy.models.dbBean.*;
import com.shxy.sudatqd.dbService.*;
import com.shxy.sudatqd.mapper.*;
import org.springframework.stereotype.Component;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Component
public class userRoleServiceImpl extends ServiceImpl<userRoleMapper, userRole> implements userRoleService {
    @Autowired
    private userRoleService userRoleService;
    @Autowired
    private roleMapper roleMapper;
    @Autowired
    private userRoleMapper userRoleMapper;
    @Autowired
    private userMapper userMapper;
    @Override
    public void modifyUserRoles(int userId, List<Integer> roleIds, int creatorId, DateTime now) {
        UpdateWrapper<userRole> wrapper = new UpdateWrapper<>();
        wrapper.eq("userId", userId);
        userRoleMapper.delete(wrapper);
        for (Integer roleId : roleIds) {
            userRole map = new userRole();
            map.setUserId(userId);
            map.setRoleId(roleId);
            map.setCreatorId(creatorId);
            map.setCreationTime(DateTime.now());
            userRoleMapper.insert(map);
        }
    }
    @Override
    public List<user> getRoleUser (int id) {
        // 1. 根据 roleId 查找角色的所有用户
        List<userRole> rus = userRoleMapper.selectList(new QueryWrapper<userRole>()
                .eq("roleId", id));

        List<user> res=new ArrayList<>();
        for (userRole ru : rus) {
            user u = userMapper.selectOne(new QueryWrapper<user>()
                    .eq("userId", ru.getUserId()));
            res.add(u);
        }
        return (res);
    }
    @Override
    public List<role> getUserRole (int id) {
        //查询用户角色
        QueryWrapper<userRole> userRoleWrapper = new QueryWrapper<>();
        userRoleWrapper.eq("userId",id);
        List<userRole> userRoles = userRoleService.list(userRoleWrapper);

        List<role> res=new ArrayList<>();
        for (userRole ru : userRoles) {
            role u = roleMapper.selectOne(new QueryWrapper<role>()
                    .eq("roleId", ru.getRoleId()));
            res.add(u);
        }
        return (res);
    }

}
