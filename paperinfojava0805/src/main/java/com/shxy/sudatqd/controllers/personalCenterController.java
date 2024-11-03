package com.shxy.sudatqd.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageInfo;
import com.shxy.models.dbBean.*;
import com.shxy.models.paramsBean.ID;
import com.shxy.models.paramsBean.personalCenter.*;
import com.shxy.models.returnBean.ResultMsg;
import com.shxy.models.views.operation.*;
import com.shxy.sudatqd.dbService.*;
import com.shxy.sudatqd.utils.JwtUtil;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
//import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@RestController
@CrossOrigin(value = "*", maxAge = 3600) //允许跨域
@RequestMapping("/PersonalCenter")
public class personalCenterController {
    @Autowired
    private userService userService;
    @Autowired
    roleService roleService;
    @Autowired
    userRoleService userRoleService;
    @Autowired
    rolePermissionService rolePermissionService;
    @Autowired
    permissionService permissionService;
    @Autowired
    commentService commentService;
    @Autowired
   replyService replyService;
    @Value("${jwtSign}")
    String jwtSign;
    @Value("${files.upload.path}")
    private String fileUploadPath;

    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    @ApiOperation(value = "修改密保",notes = "修改密保")
    @PostMapping("/changeQues")
    @Transactional
    public ResultMsg<Boolean> changeQues(@RequestBody quesAndAnswer data) {
        try {
            //拿到token进行解析，拿到当前登录用户信息，返回即可
            String token_id = getHttpServletRequest().getHeader("Authorization");
            if (token_id == null || token_id.isEmpty()) {
                return new ResultMsg<>(true, "登录已失效，请重新登录！", 200, null);
            } else {
                String username = getUsernameFromToken(token_id);

                QueryWrapper<user> oneuser = new QueryWrapper<>();
                oneuser.eq("userName", username);
                user one = userService.getOne(oneuser);
                if(Objects.equals(data.getQuesId(), one.getQuesId())&& Objects.equals(data.getAnswer(), one.getAnswer())){
                    return new ResultMsg<>(false, "请作出修改后再进行提交", 200, null);
                } else{
                    boolean update = false;
                    UpdateWrapper<user> usersUpdateWrapper = new UpdateWrapper<>();
                    usersUpdateWrapper.eq("userName", username);
                    usersUpdateWrapper.set("quesId", data.getQuesId());
                    usersUpdateWrapper.set("answer", data.getAnswer());
                    update = userService.update(usersUpdateWrapper);
                    if (update) {
                        return new ResultMsg<>(true, "密保修改成功", 200, update);
                    } else {
                        return new ResultMsg<>(true, "操作失败，请重试", 205, null);
                    }
                }
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "修改头像",notes = "修改头像")
    @PostMapping("/changeHeadPic")
    @Transactional
    public ResultMsg<Boolean> changeHeadPic(@RequestBody MultipartFile file) {
        try {
            //拿到token进行解析，拿到当前登录用户信息，返回即可
            String token_id = getHttpServletRequest().getHeader("Authorization");
            if (token_id == null || token_id.isEmpty()) {
                return new ResultMsg<>(true, "登录已失效，请重新登录！", 200, null);
            } else {
                String username = getUsernameFromToken(token_id);
                //删除原来的头像
                QueryWrapper<user> oneuser = new QueryWrapper<>();
                oneuser.eq("userName", username);
                user one = userService.getOne(oneuser);
                if(!Objects.equals(one.getPicture(), "src/main/resources/headpics/headpic.jpg")){
                    boolean dele = deletePicture(System.getProperty("user.dir")+"/"+one.getPicture());;
                }
                //修改头像
                boolean update = false;
                UpdateWrapper<user> usersUpdateWrapper = new UpdateWrapper<>();
                usersUpdateWrapper.eq("userName", username);
                // 将上传的文件保存到指定的路径
                String filePath = System.getProperty("user.dir")+"//Users/kwai/Desktop/picture/" + file.getOriginalFilename();
                File destinationFile = new File(filePath);
                file.transferTo(destinationFile);
                //修改user表中的头像路径
                usersUpdateWrapper.set("picture", "/Users/kwai/Desktop/picture/" + file.getOriginalFilename());
                update = userService.update(usersUpdateWrapper);
                if (update) {
                    return new ResultMsg<>(true, "头像修改成功", 200, update);
                } else {
                    return new ResultMsg<>(true, "操作失败，请重试", 205, null);
                }
            }

        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }
    @ApiOperation(value = "获取个人中心基本信息",notes = "获取个人中心基本信息")
    @PostMapping("/getUserCenterInfo")
    @Transactional
    public ResultMsg<userCenterInfo> getUserCenterInfo() {
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
                //构造返回结果
                userCenterInfo res = new userCenterInfo();
                res.setOne(userService.getOne(list));
                res.setRoleList(userRoleService.getUserRole(oneId));
                res.setPermissionList(userService.getUserPermissionList(oneId));
                //返回结果
                return new ResultMsg<>(true, "获取成功", 200, res);
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "获取个人发表的评论分页查询列表",notes = "获取个人发表的评论分页查询列表")
    @PostMapping("/getUserCommentList")
    @Transactional
    public ResultMsg<PageInfo<commentItem>> getUserCommentList(@RequestBody userCommentQuery data) {
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
                String oneName=userService.getOne(list).getUserName();
                PageInfo<commentItem> res = userService.UserCommentList(oneId,oneName,data);
                //返回结果
                return new ResultMsg<>(true, "获取成功", 200, res);
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "获取个人发表的回复分页查询列表",notes = "获取个人发表的回复分页查询列表")
    @PostMapping("/getUserReplyList")
    @Transactional
    public ResultMsg<PageInfo<replyItem>> getUserReplyList(@RequestBody userReplyQuery data) {
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
                String oneName=userService.getOne(list).getUserName();
                PageInfo<replyItem> res = userService.UserReplyList(oneId,oneName,data);
                //返回结果
                return new ResultMsg<>(true, "获取成功", 200, res);
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "获取个人发表的投诉分页查询列表",notes = "获取个人发表的投诉分页查询列表")
    @PostMapping("/getUserComplainList")
    @Transactional
    public ResultMsg<PageInfo<complainItem>> getUserComplainList(@RequestBody userComplainQuery data) {
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
                String oneName=userService.getOne(list).getUserName();
                PageInfo<complainItem> res = userService.UserComplainList(oneId,oneName,data);
                //返回结果
                return new ResultMsg<>(true, "获取成功", 200, res);
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "获取个人发表的反馈分页查询列表",notes = "获取个人发表的反馈分页查询列表")
    @PostMapping("/getUserFeedbackList")
    @Transactional
    public ResultMsg<PageInfo<feedbackItem>> getUserFeedbackList(@RequestBody userFeedbackQuery data) {
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
                String oneName=userService.getOne(list).getUserName();
                PageInfo<feedbackItem> res = userService.UserFeedbackList(oneId,oneName,data);
                //返回结果
                return new ResultMsg<>(true, "获取成功", 200, res);
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "删除发表过的评论",notes = "删除发表过的评论")
    @PostMapping("/deleteUserComment")
    @Transactional
    public ResultMsg<Boolean> deleteUserComment(@RequestBody ID data) {
        try {
            //拿到token进行解析，拿到当前登录用户信息，返回即可
            String token_id = getHttpServletRequest().getHeader("Authorization");
            if (token_id == null || token_id.isEmpty()) {
                return new ResultMsg<>(true, "登录已失效，请重新登录！", 200, null);
            } else {
                String username = getUsernameFromToken(token_id);

                QueryWrapper<user> oneuser = new QueryWrapper<>();
                oneuser.eq("userName", username);
                user one = userService.getOne(oneuser);
                boolean update = false;
                UpdateWrapper<comment> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("commentId", data.getId());
                updateWrapper.set("commentState", 1);
                update = commentService.update(updateWrapper);
                if (update) {
                    return new ResultMsg<>(true, "已成功删除", 200, update);
                } else {
                    return new ResultMsg<>(true, "操作失败，请重试", 205, null);
                }
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }
    @ApiOperation(value = "编辑个人信息",notes = "编辑个人信息")
    @Transactional
    @PostMapping("/editUser")
    public ResultMsg<Boolean> editUser(
            @RequestParam("userName") String userName,
            @RequestParam("picture") String picture,
            @RequestParam("userPass") String userPass,
            @RequestParam("answer") String answer,
            @RequestParam("quesId") Integer quesId,
            @RequestParam("userId") Integer userId
//            @RequestParam(value = "file", required = false) MultipartFile file
    ) throws IOException {
        try {
            if (picture == null) {
                QueryWrapper<user> uQueryWrapper = new QueryWrapper<>();
                Integer uid = userId; // 获取用户ID
                String uname = userName;
                String upassword = userPass;
                Integer quesIdd = quesId;
                String answerd = answer;
                UpdateWrapper<user> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("userId", uid);
                updateWrapper.set("userName", uname);
                updateWrapper.set("userPass", upassword);
                updateWrapper.set("quesId", quesIdd);
                updateWrapper.set("answer", answerd);
//                updateWrapper.set("email", status);
                boolean update = userService.update(null, updateWrapper);
                if (update) {
                    return new ResultMsg<>(true, "操作成功", 200, update);
                } else {
                    return new ResultMsg<>(true, "操作失败，请重试", 200, null);
                }

            } else {
                // 判断是否具有相应权限

                String[] parts = picture.split(":");
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

//                String phob = "http://localhost/suda/" + fileName;//先上传
//                String phob = "src/main/resources/pictures/" + fileName;
                Integer uid = userId; // 获取用户ID
                String uname = userName;
                String upassword = userPass;
                Integer quesIdd = quesId;
                String answerd = answer;
                // 数据库修改操作
                UpdateWrapper<user> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("userId", uid);
                updateWrapper.set("userName", uname);
                updateWrapper.set("userPass", upassword);
                updateWrapper.set("picture", "src/main/resources/picutres/headPics/"+ decodedFileName);
                updateWrapper.set("quesId", quesIdd);
                updateWrapper.set("answer", answerd);
//                updateWrapper.set("email", status);
                boolean update = userService.update(null, updateWrapper);


                if (update) {
                    return new ResultMsg<>(true, "操作成功", 200, update);
                } else {
                    return new ResultMsg<>(true, "操作失败，请重试", 200, null);
                }
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "删除发表过的回复",notes = "删除发表过的回复")
    @PostMapping("/deleteUserReply")
    @Transactional
    public ResultMsg<Boolean> deleteUserReply(@RequestBody ID data) {
        try {
            //拿到token进行解析，拿到当前登录用户信息，返回即可
            String token_id = getHttpServletRequest().getHeader("Authorization");
            if (token_id == null || token_id.isEmpty()) {
                return new ResultMsg<>(true, "登录已失效，请重新登录！", 200, null);
            } else {
                String username = getUsernameFromToken(token_id);
                QueryWrapper<user> oneuser = new QueryWrapper<>();
                oneuser.eq("userName", username);
                user one = userService.getOne(oneuser);
                boolean update = false;
                UpdateWrapper<reply> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("replyId", data.getId());
                updateWrapper.set("replyState", 0);
                update = replyService.update(updateWrapper);
                if (update) {
                    return new ResultMsg<>(true, "已成功删除", 200, update);
                } else {
                    return new ResultMsg<>(true, "操作失败，请重试", 205, null);
                }
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

    //删除图片
    public Boolean deletePicture(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            if (file.delete()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class userCenterInfo {
        private user one;
        private List<role> roleList;
        private List<permission> permissionList;
    }

}
