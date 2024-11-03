package com.shxy.sudatqd.controllers;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageInfo;
import com.shxy.models.dbBean.*;
import com.shxy.models.paramsBean.*;
import com.shxy.models.paramsBean.archive.archiveQuery;
import com.shxy.models.paramsBean.login.*;
import com.shxy.models.returnBean.ResultMsg;
import com.shxy.models.views.archiveItem;
import com.shxy.sudatqd.dbService.*;
import com.shxy.sudatqd.utils.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.ApiOperation;

import java.io.File;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.xml.bind.DatatypeConverter;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@CrossOrigin(value = "*", maxAge = 3600) //允许跨域
@RequestMapping("/Login")
public class loginController {
    @Autowired
    userService userService;
    @Autowired
    userRoleService userRoleService;
    @Autowired
    questionService questionService;
    @Value("${jwtSign}")
    String jwtSign;

    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    @ApiOperation(value = "用户注册",notes = "用户注册")
    @PostMapping("/register")
    @Transactional
    public ResultMsg<Boolean> register(@RequestBody registInfo data) {
        try {
            // 检查是否重名
            QueryWrapper<user> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userName", data.getUsername());
            user existingUser = userService.getOne(queryWrapper);
            if (existingUser != null) {
                // 如果用户名存在，不允许注册
                return new ResultMsg<>(false, "用户名已存在，请选择其他用户名", 409, null);
            }
            String[] parts = data.getPicture().split(":");
            String decodedFileName = parts[0];
            byte[] bytes = Base64Utils.decodeFromString(parts[1]);
            //获取Base64，并解码成字节
            String filePath = System.getProperty("user.dir")+"/src/main/resources/pictures/headPics"+"/" + decodedFileName;
            File file = new File(filePath);
            //判断文件所在目录是否存在，如果不存在，则就创建目录
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            //创建新的图片文件
            file.createNewFile();
            //创建文件输出流
            FileOutputStream fos = new FileOutputStream(file);
            //将字节写入到图片文件中
            fos.write(bytes);
            fos.close();
            System.out.println(true);

            // 新增用户
            user addnew = new user();
            addnew.setState(1);
            addnew.setUserName(data.getUsername());
            addnew.setUserPass(hashPassword(data.getUserpass()));
            addnew.setQuesId(data.getQuesId());
            addnew.setAnswer(data.getAnswer());
            addnew.setPicture("src/main/resources/picutres/headPics/"+ decodedFileName);
            // 新增用户，返回结果
            boolean update1 = userService.save(addnew);

            //获取用户id
            QueryWrapper<user> newWrapper = new QueryWrapper<>();
            newWrapper.eq("userName", data.getUsername());
            Integer id = userService.getOne(queryWrapper).getUserId();
            //赋予普通用户的角色
            userRole newOne = new userRole();
            newOne.setUserId(id);
            newOne.setRoleId(2);
            newOne.setCreatorId(id);
            newOne.setCreationTime(DateTime.now());
            boolean update2 = userRoleService.save(newOne);

            if (update1&&update2) {
                return new ResultMsg<>(true, "注册成功", 200, true);
            } else {
                return new ResultMsg<>(false, "注册失败，请重试", 200, null);
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "用户登录",notes = "用户登录")
    @PostMapping("/loginResult")
    @Transactional
    public ResultMsg<LoginResult> loginResult(@RequestBody User data) {
        try {
            QueryWrapper<user> wrapper = new QueryWrapper<>();
            if (data.getUsername() != null && !data.getUsername().isEmpty()) {
                wrapper.eq("userName", data.getUsername());
            }
            user oneuser = userService.getOne(wrapper);
            String result = "";
            if (oneuser == null)// 用户不存在
            {
                result = "用户不存在！";
                return new ResultMsg<>(true, result, 200, null);
            } else if (oneuser.getState().equals(0))// 用户已禁用
            {
                result = "该用户已被禁用！";
                return new ResultMsg<>(true, result, 200, null);
            } else {
                //判断密码的正误
                String submit_pwd = hashPassword(data.getUserpass()); //获取用户输入密码
                String correct_pwd = oneuser.getUserPass(); //查询正确密码
                System.out.println(hashPassword(data.getUserpass()));
                //将输入密码和正确密码对比
                if (correct_pwd.equals(submit_pwd)) {
                    String token = JwtUtil.getToken(new User(data.getUsername(), data.getUserpass()), jwtSign);
                    LoginResult res = new LoginResult();
                    res.userId = oneuser.getUserId();
                    res.username = oneuser.getUserName();
                    res.token = token;
                    return new ResultMsg<>(true, "获取成功", 200, res);
                } else {
                    result = "密码错误！请重试";
                    return new ResultMsg<>(true, result, 200, null);
                }
            }
        } catch (Exception e) {
            String result = e.toString();
            return new ResultMsg<>(false, result, 500, null);
        }
    }

    @ApiOperation(value = "退出登录",notes = "退出登录")
    @PostMapping("/logOut")
    @Transactional
    public ResultMsg<Boolean> logOut() {
        try {
            //拿到token进行解析，拿到当前登录用户信息，返回即可
            String token_id = getHttpServletRequest().getHeader("Authorization");
            if (token_id == null || token_id.isEmpty()) {
                return new ResultMsg<>(true, "登录已失效，请重新登录！", 200, null);
            } else {
                return new ResultMsg<>(true, "已退出", 200, null);
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "根据用户名获取用户信息",notes = "根据用户名获取用户信息")
    @PostMapping("/getOneUser")
    @Transactional
    public ResultMsg<user> getOneUser(@RequestBody Name data) {
        try {
            QueryWrapper<user> oneuser = new QueryWrapper<>();
            oneuser.eq("userName", data.getName());
            user one = userService.getOne(oneuser);
            if(one!=null){
                return new ResultMsg<>(true, "获取成功", 200, one);
            } else{
                return new ResultMsg<>(false, "未查询到用户信息！", 200, null);
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "验证密保",notes = "验证密保")
    @PostMapping("/checkQues")
    @Transactional
    public ResultMsg<Boolean> checkQues(@RequestBody checkQuesParam data) {
        try {
            String username = data.getUsername();
            QueryWrapper<user> oneuser = new QueryWrapper<>();
            oneuser.eq("userName", username);
            user one = userService.getOne(oneuser);
            if(Objects.equals(data.getAnswer(), one.getAnswer())){
                return new ResultMsg<>(true, "获取成功", 200, true);
            } else{
                return new ResultMsg<>(false, "密保验证失败！", 200, null);
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "重置密码",notes = "重置密码")
    @PostMapping("/resetPass")
    @Transactional
    public ResultMsg<Boolean> resetPass(@RequestBody User data) {
        try {
            QueryWrapper<user> oneuser = new QueryWrapper<>();
            oneuser.eq("userName", data.getUsername());
            user one = userService.getOne(oneuser);
            if(Objects.equals(hashPassword(data.getUserpass()), one.getUserPass())){
                return new ResultMsg<>(false, "新密码请勿与旧密码相同", 481, null);
            } else{
                boolean update = false;
                UpdateWrapper<user> usersUpdateWrapper = new UpdateWrapper<>();
                usersUpdateWrapper.eq("userName", data.getUsername());
                usersUpdateWrapper.set("userPass", hashPassword(data.getUserpass()));
                update = userService.update(usersUpdateWrapper);
                if (update) {
                    return new ResultMsg<>(true, "重置密码成功", 200, update);
                } else {
                    return new ResultMsg<>(true, "操作失败，请重试", 205, null);
                }
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "获取密保问题",notes = "获取密保问题列表")
    @PostMapping("/getQuesList")
    @Transactional
    public ResultMsg<List<question>> getQuesList() {
        try {
            QueryWrapper<question> ques = new QueryWrapper<>();
            List<question> one = questionService.list(ques);
            if(one!=null){
                return new ResultMsg<>(true, "获取成功", 200, one);
            } else{
                return new ResultMsg<>(false, "未查询到相关信息！", 200, null);
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    // 密码加密
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);
            digest.update(passwordBytes);
            byte[] hashedBytes = digest.digest();
            String hashedPassword = DatatypeConverter.printHexBinary(hashedBytes).toLowerCase();
            return hashedPassword;
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Handle exception appropriately based on your application's requirements
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
    class LoginResult {
        private Integer userId;
        private String username;
        private String token;
    }
}
