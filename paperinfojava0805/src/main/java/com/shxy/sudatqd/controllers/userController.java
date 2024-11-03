package com.shxy.sudatqd.controllers;

import cn.hutool.core.date.DateTime;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageInfo;
import com.shxy.models.dbBean.*;
import com.shxy.models.paramsBean.ID;
import com.shxy.models.paramsBean.paper.paperQuery;
import com.shxy.models.paramsBean.user.changeSon;
import com.shxy.models.paramsBean.user.changeStatus;
import com.shxy.models.paramsBean.user.userQuery;
import com.shxy.models.returnBean.ResultMsg;
import com.shxy.models.views.paperItem;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin(value = "*", maxAge = 3600) //允许跨域
@RequestMapping("/User")
public class userController {
    @Autowired
    private userService userService;
    @Autowired
    private userRoleService userRoleService;
    @Value("${jwtSign}")
    String jwtSign;

    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    @ApiOperation(value = "获取用户列表",notes = "获取用户列表")
    @PostMapping("/getUserList")
    @Transactional
    public ResultMsg<List<user>> getUserList() {
        try {
            QueryWrapper<user> list = new QueryWrapper<>();
            list.eq("state", 1);
            List<user> oneList = userService.list(list);
            return new ResultMsg<>(true, "获取成功", 200, oneList);
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "编辑用户状态",notes = "编辑用户状态")
    @PostMapping("/changeUserStatus")
    @Transactional
    public ResultMsg<Boolean> changeUserStatus(@RequestBody changeStatus data) {
        try {
            //拿到token进行解析，拿到当前登录用户信息，返回即可
            String token_id = getHttpServletRequest().getHeader("Authorization");
            if (token_id == null || token_id.isEmpty()) {
                return new ResultMsg<>(true, "登录已失效，请重新登录！", 200, null);
            } else {
                boolean update = false;
                UpdateWrapper<user> usersUpdateWrapper = new UpdateWrapper<>();
                usersUpdateWrapper.eq("userId",data.getId());
                usersUpdateWrapper.set("state", data.getStatus());
                update = userService.update(usersUpdateWrapper);
                if (update) {
                    return new ResultMsg<>(true, "用户状态已更新", 200, update);
                } else {
                    return new ResultMsg<>(true, "操作失败，请重试", 205, null);
                }
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "编辑用户角色",notes = "编辑用户角色")
    @PostMapping("/changeUserRole")
    public ResultMsg<Boolean> changeUserRole(@RequestBody changeSon data) {
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
                //修改角色
                userRoleService.modifyUserRoles(data.getId(),data.getList(),oneId, DateTime.now());
                return new ResultMsg<>(true, "用户角色已更新", 200, true);
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "获取用户分页查询列表",notes = "获取用户分页查询列表")
    @PostMapping("/getUserPageList")
    @Transactional
    public ResultMsg<PageInfo<user>> getUserPageList(@RequestBody userQuery data) {
        try {
            //拿到token进行解析，拿到当前登录用户信息，返回即可
            String token_id = getHttpServletRequest().getHeader("Authorization");
            if (token_id == null || token_id.isEmpty()) {
                return new ResultMsg<>(true, "登录已失效，请重新登录！", 200, null);
            } else {
                PageInfo<user> pageList= userService.userPage(data);
                return new ResultMsg<>(true, "获取成功", 200, pageList);
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "获取用户详情",notes = "获取用户详情")
    @PostMapping("/getUserDetail")
    public ResultMsg<userDetail> getUserDetail(@RequestBody ID data) {
        try {
            //拿到token进行解析，拿到当前登录用户信息，返回即可
            String token_id = getHttpServletRequest().getHeader("Authorization");
            if (token_id == null || token_id.isEmpty()) {
                return new ResultMsg<>(true, "登录已失效，请重新登录！", 200, null);
            } else {
                //当前登录用户的id
                QueryWrapper<user> list = new QueryWrapper<>();
                list.eq("userId",data.getId());
                Integer oneId=userService.getOne(list).getUserId();
                //构造返回结果
                userDetail res = new userDetail();
                res.setOne(userService.getOne(list));
                res.setRoleList(userRoleService.getUserRole(oneId));
                res.setPermissionList(userService.getUserPermissionList(oneId));
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
    class userDetail {
        private user one;
        private List<role> roleList;
        private List<permission> permissionList;
    }
}
