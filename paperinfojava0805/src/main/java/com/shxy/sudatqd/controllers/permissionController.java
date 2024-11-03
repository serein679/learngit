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
import com.shxy.models.paramsBean.user.*;
import com.shxy.models.returnBean.ResultMsg;
import com.shxy.models.views.permissionItem;
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
@RequestMapping("/Permission")
public class permissionController {
    @Autowired
    private userService userService;
    @Autowired
    private roleService roleService;
    @Autowired
    private rolePermissionService rolePermissionService;
    @Autowired
    private permissionService permissionService;
    @Value("${jwtSign}")
    String jwtSign;
    @Autowired
    private userRoleMapper userRoleMapper;

    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    @ApiOperation(value = "获取权限列表",notes = "获取权限列表")
    @PostMapping("/getPermissionList")
    @Transactional
    public ResultMsg<List<permission>> getPermissionList() {
        try {
            QueryWrapper<permission> list = new QueryWrapper<>();
            list.eq("state", 1);
            List<permission> oneList = permissionService.list(list);
            return new ResultMsg<>(true, "获取成功", 200, oneList);
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "编辑权限描述",notes = "编辑权限描述")
    @PostMapping("/editPermission")
    @Transactional
    public ResultMsg<Boolean> editPermission(@RequestBody editPermissionParam data) {
        try {
            //拿到token进行解析，拿到当前登录用户信息，返回即可
            String token_id = getHttpServletRequest().getHeader("Authorization");
            if (token_id == null || token_id.isEmpty()) {
                return new ResultMsg<>(true, "登录已失效，请重新登录！", 200, null);
            } else {
                boolean update = false;
                UpdateWrapper<permission> permissionUpdateWrapper = new UpdateWrapper<>();
                permissionUpdateWrapper.eq("permissionId",data.getId());
                permissionUpdateWrapper.set("description", data.getDescription());
                update = permissionService.update(permissionUpdateWrapper);
                if (update) {
                    return new ResultMsg<>(true, "权限描述已更新", 200, update);
                } else {
                    return new ResultMsg<>(true, "操作失败，请重试", 205, null);
                }
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }
    @ApiOperation(value = "获取权限分页查询列表",notes = "获取权限分页查询列表")
    @PostMapping("/getPermissionPageList")
    @Transactional
    public ResultMsg<PageInfo<permissionItem>> getPermissionPageList(@RequestBody permissionQuery data) {
        try {
            //拿到token进行解析，拿到当前登录用户信息，返回即可
            String token_id = getHttpServletRequest().getHeader("Authorization");
            if (token_id == null || token_id.isEmpty()) {
                return new ResultMsg<>(true, "登录已失效，请重新登录！", 200, null);
            } else {
                PageInfo<permissionItem> pageList= permissionService.permissionPage(data);
                return new ResultMsg<>(true, "获取成功", 200, pageList);
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "获取权限详情",notes = "获取权限详情")
    @PostMapping("/getPermissionDetail")
    public ResultMsg<permissionDetail> getPermissionDetail(@RequestBody ID data) {
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
                permissionDetail res=new permissionDetail();
                res.permissionItem=permissionService.getPermisisonItem(data.getId());
                res.roleList=rolePermissionService.getPermissionRole(data.getId());
                res.userList=permissionService.getPermissionUser(data.getId());
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
    class permissionDetail {
        private permissionItem permissionItem;
        private List<role> roleList;
        private List<user> userList;
    }
}
