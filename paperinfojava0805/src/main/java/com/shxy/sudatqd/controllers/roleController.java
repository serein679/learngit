package com.shxy.sudatqd.controllers;

import cn.hutool.core.date.DateTime;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageInfo;
import com.shxy.models.dbBean.permission;
import com.shxy.models.dbBean.role;
import com.shxy.models.dbBean.user;
import com.shxy.models.paramsBean.ID;
import com.shxy.models.paramsBean.paper.paperQuery;
import com.shxy.models.paramsBean.user.addRoleParam;
import com.shxy.models.paramsBean.user.changeSon;
import com.shxy.models.paramsBean.user.changeStatus;
import com.shxy.models.paramsBean.user.roleQuery;
import com.shxy.models.returnBean.ResultMsg;
import com.shxy.models.views.paperItem;
import com.shxy.models.views.roleItem;
import com.shxy.sudatqd.dbService.*;
import com.shxy.sudatqd.mapper.userRoleMapper;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin(value = "*", maxAge = 3600) //允许跨域
@RequestMapping("/Role")
public class roleController {
    @Autowired
    private userService userService;
    @Autowired
    private userRoleService userRoleService;
    @Autowired
    private roleService roleService;
    @Autowired
    private rolePermissionService rolePermissionService;
    @Value("${jwtSign}")
    String jwtSign;


    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    @ApiOperation(value = "获取角色列表",notes = "获取角色列表")
    @PostMapping("/getRoleList")
    @Transactional
    public ResultMsg<List<role>> getRoleList() {
        try {
            QueryWrapper<role> list = new QueryWrapper<>();
            list.eq("state", 1);
            List<role> oneList = roleService.list(list);
            return new ResultMsg<>(true, "获取成功", 200, oneList);
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "编辑角色状态",notes = "编辑角色状态")
    @PostMapping("/changeRoleStatus")
    @Transactional
    public ResultMsg<Boolean> changeRoleStatus(@RequestBody changeStatus data) {
        try {
            //拿到token进行解析，拿到当前登录用户信息，返回即可
            String token_id = getHttpServletRequest().getHeader("Authorization");
            if (token_id == null || token_id.isEmpty()) {
                return new ResultMsg<>(true, "登录已失效，请重新登录！", 200, null);
            } else {
                boolean update = false;
                UpdateWrapper<role> rolesUpdateWrapper = new UpdateWrapper<>();
                rolesUpdateWrapper.eq("roleId",data.getId());
                rolesUpdateWrapper.set("state", data.getStatus());
                update = roleService.update(rolesUpdateWrapper);
                if (update) {
                    return new ResultMsg<>(true, "角色状态已更新", 200, update);
                } else {
                    return new ResultMsg<>(true, "操作失败，请重试", 205, null);
                }
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "编辑角色权限",notes = "编辑角色权限")
    @PostMapping("/changeRolePermission")
    public ResultMsg<Boolean> changeRolePermission(@RequestBody changeSon data) {
        try {
            //拿到token进行解析，拿到当前登录用户信息，返回即可
            String token_id = getHttpServletRequest().getHeader("Authorization");
            if (token_id == null || token_id.isEmpty()) {
                return new ResultMsg<>(true, "登录已失效，请重新登录！", 200, null);
            } else {
                //当前登录用户的id
                QueryWrapper<user> list = new QueryWrapper<>();
                list.eq("username",getUsernameFromToken(token_id));
                Integer oneId=userService.getOne(list).getUserId();
                //修改权限
                rolePermissionService.modifyRolePermissions(data.getId(),data.getList(),oneId, DateTime.now());
                return new ResultMsg<>(true, "角色权限已更新", 200, true);
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "新增角色",notes = "新增角色")
    @PostMapping("/addRole")
    public ResultMsg<Boolean> addRole(@RequestBody addRoleParam data) {
        try {
            //拿到token进行解析，拿到当前登录用户信息，返回即可
            String token_id = getHttpServletRequest().getHeader("Authorization");
            if (token_id == null || token_id.isEmpty()) {
                return new ResultMsg<>(true, "登录已失效，请重新登录！", 200, null);
            } else {
                //当前登录用户的id
                QueryWrapper<user> list = new QueryWrapper<>();
                list.eq("username",getUsernameFromToken(token_id));
                Integer oneId=userService.getOne(list).getUserId();
                //新增角色
                roleService.addRole(data,oneId,DateTime.now());
                return new ResultMsg<>(true, "新增角色成功！", 200, true);
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }
    @ApiOperation(value = "获取角色分页查询列表",notes = "获取角色分页查询列表")
    @PostMapping("/getRolePageList")
    @Transactional
    public ResultMsg<PageInfo<roleItem>> getRolePageList(@RequestBody roleQuery data) {
        try {
            //拿到token进行解析，拿到当前登录用户信息，返回即可
            String token_id = getHttpServletRequest().getHeader("Authorization");
            if (token_id == null || token_id.isEmpty()) {
                return new ResultMsg<>(true, "登录已失效，请重新登录！", 200, null);
            } else {
                PageInfo<roleItem> pageList= roleService.rolePage(data);
                return new ResultMsg<>(true, "获取成功", 200, pageList);
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "获取角色详情",notes = "获取角色详情")
    @PostMapping("/getRoleDetail")
    public ResultMsg<roleDetail> getRoleDetail(@RequestBody ID data) {
        try {
            //拿到token进行解析，拿到当前登录用户信息，返回即可
            String token_id = getHttpServletRequest().getHeader("Authorization");
            if (token_id == null || token_id.isEmpty()) {
                return new ResultMsg<>(true, "登录已失效，请重新登录！", 200, null);
            } else {
                //当前登录用户的id
                QueryWrapper<user> list = new QueryWrapper<>();
                list.eq("username",getUsernameFromToken(token_id));
                Integer oneId=userService.getOne(list).getUserId();
                //获取角色Item、角色的权限列表和拥有该角色的用户列表
                roleDetail res=new roleDetail();
                res.roleItem=roleService.getRoleItem(data.getId());
                res.permissionList=rolePermissionService.getRolePermission(data.getId());
                res.userList=userRoleService.getRoleUser(data.getId());
                return new ResultMsg<>(true, "success", 200, res);
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }



    //解析当前登录的用户名
    public String getUsernameFromToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(jwtSign);
        DecodedJWT decodedJWT = JWT.require(algorithm)
                .build()
                .verify(token);
        return decodedJWT.getClaim("username").asString();
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class roleDetail {
        private roleItem roleItem;
        private List<permission> permissionList;
        private List<user> userList;
    }
}
