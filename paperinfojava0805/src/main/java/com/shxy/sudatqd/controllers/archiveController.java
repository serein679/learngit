package com.shxy.sudatqd.controllers;

import cn.hutool.core.date.DateTime;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageInfo;
import com.shxy.models.dbBean.archive;
import com.shxy.models.dbBean.permission;
import com.shxy.models.dbBean.user;
import com.shxy.models.paramsBean.ID;
import com.shxy.models.paramsBean.archive.addArchiveParam;
import com.shxy.models.paramsBean.archive.archiveQuery;
import com.shxy.models.paramsBean.archive.editArchiveParam;
import com.shxy.models.paramsBean.paper.paperQuery;
import com.shxy.models.paramsBean.user.changeStatus;
import com.shxy.models.paramsBean.user.editPermissionParam;
import com.shxy.models.returnBean.ResultMsg;
import com.shxy.models.views.*;
//import com.shxy.models.views.operation.archiveComponentItem;
import com.shxy.models.views.operation.commentItem;
import com.shxy.sudatqd.dbService.permissionService;
import com.shxy.sudatqd.dbService.*;
import com.shxy.sudatqd.mapper.archiveMapper;
import com.shxy.sudatqd.mapper.archivePaperMapper;
import com.shxy.sudatqd.mapper.commentMapper;
import com.shxy.sudatqd.mapper.archiveComponentMapper;
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
@RequestMapping("/Archive")
public class archiveController {
    @Autowired
    private userService userService;
    @Autowired
    private archiveMapper archiveMapper;
    @Autowired
    private com.shxy.sudatqd.mapper.archivePaperMapper archivePaperMapper;
    @Autowired
    private com.shxy.sudatqd.mapper.commentMapper commentMapper;
    @Autowired
    private com.shxy.sudatqd.mapper.pictureMapper pictureMapper;
    @Autowired
    private com.shxy.sudatqd.mapper.recordingMapper recordingMapper;
    @Autowired
    private com.shxy.sudatqd.mapper.archiveComponentMapper archiveComponentMapper;
    @Autowired
    private archiveService archiveService;
    @Autowired
    private rolePermissionService rolePermissionService;
    @Value("${jwtSign}")
    String jwtSign;

    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    @ApiOperation(value = "获取档案列表", notes = "获取档案列表")
    @PostMapping("/getArchiveList")
    @Transactional
    public ResultMsg<List<archive>> getArchiveList() {
        try {
            QueryWrapper<archive> list = new QueryWrapper<>();
            List<archive> oneList = archiveService.list(list);
            return new ResultMsg<>(true, "获取成功", 200, oneList);
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "获取档案列表", notes = "获取档案列表")
    @PostMapping("/getArchiveLists")
    @Transactional
    public ResultMsg<List<archive>> getArchiveLists() {
        try {
            QueryWrapper<archive> list = new QueryWrapper<>();
            list.eq("archiveStateType", 0)
                    .last("LIMIT 8");
            List<archive> oneList = archiveService.list(list);
            return new ResultMsg<>(true, "获取成功", 200, oneList);
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }


    @ApiOperation(value = "获取档案首页列表", notes = "获取档案首页列表")
    @PostMapping("/getArchiveHomeList")
    @Transactional
    public ResultMsg<List<archiveHome>> getArchiveHomeList() {
        try {
            QueryWrapper<archiveHome> list = new QueryWrapper<>();
            List<archiveHome> archiveHome = archiveService.listHome(list);
            return new ResultMsg<>(true, "获取成功", 200, archiveHome);
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "编辑档案基本信息", notes = "编辑档案基本信息")
    @PostMapping("/editArchive")
    @Transactional
    public ResultMsg<Boolean> editArchive(@RequestBody editArchiveParam data) {
        try {
            //拿到token进行解析，拿到当前登录用户信息，返回即可
            String token_id = getHttpServletRequest().getHeader("Authorization");
            if (token_id == null || token_id.isEmpty()) {
                return new ResultMsg<>(true, "登录已失效，请重新登录！", 200, null);
            } else {
                UpdateWrapper<archive> archiveUpdateWrapper = new UpdateWrapper<>();
                archiveUpdateWrapper.eq("archiveId", data.getArchiveId());
                archiveUpdateWrapper
                        .set("archiveName", data.getArchiveName())
                        .set("institutionId", data.getInstitutionId())
                        .set("archiveType", data.getArchiveType())
                        .set("archiveContent", data.getArchiveContent());
                boolean update = archiveService.update(archiveUpdateWrapper);
                if (update) {
                    return new ResultMsg<>(true, "档案信息已更新！", 200, update);
                } else {
                    return new ResultMsg<>(true, "操作失败，请重试", 205, null);
                }
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "编辑档案状态", notes = "编辑档案状态")
    @PostMapping("/changeArchiveStatus")
    @Transactional
    public ResultMsg<Boolean> changeArchiveStatus(@RequestBody changeStatus data) {
        try {
            //拿到token进行解析，拿到当前登录用户信息，返回即可
            String token_id = getHttpServletRequest().getHeader("Authorization");
            if (token_id == null || token_id.isEmpty()) {
                return new ResultMsg<>(true, "登录已失效，请重新登录！", 200, null);
            } else {
                UpdateWrapper<archive> archiveUpdateWrapper = new UpdateWrapper<>();
                archiveUpdateWrapper.eq("archiveId", data.getId());
                archiveUpdateWrapper
                        .set("archiveStateType", data.getStatus());
                boolean update = archiveService.update(archiveUpdateWrapper);
                if (update) {
                    return new ResultMsg<>(true, "档案已删除", 200, update);
                } else {
                    return new ResultMsg<>(true, "操作失败，请重试", 205, null);
                }
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "获取档案分页查询列表", notes = "获取档案分页查询列表")
    @PostMapping("/getArchivePageList")
    @Transactional
    public ResultMsg<PageInfo<archiveItem>> getArchivePageList(@RequestBody archiveQuery data) {
        try {
            //拿到token进行解析，拿到当前登录用户信息，返回即可
            String token_id = getHttpServletRequest().getHeader("Authorization");
            if (token_id == null || token_id.isEmpty()) {
                return new ResultMsg<>(true, "登录已失效，请重新登录！", 200, null);
            } else {
                PageInfo<archiveItem> pageList = archiveService.archivePage(data);
                return new ResultMsg<>(true, "获取成功", 200, pageList);
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "新增档案", notes = "新增档案")
    @PostMapping("/addArchive")
    @Transactional
    public ResultMsg<Boolean> addArchive(@RequestBody addArchiveParam data) {
        try {
            //拿到token进行解析，拿到当前登录用户信息，返回即可
            String token_id = getHttpServletRequest().getHeader("Authorization");
            if (token_id == null || token_id.isEmpty()) {
                return new ResultMsg<>(true, "登录已失效，请重新登录！", 200, null);
            } else {
                //当前登录用户的id
                QueryWrapper<user> list = new QueryWrapper<>();
                list.eq("username", getUsernameFromToken(token_id));
                Integer oneId = userService.getOne(list).getUserId();
                archiveService.addArchive(data, oneId, DateTime.now());
                List<Integer> pids = rolePermissionService.userPermission(oneId);
                String res = "";
                if (pids.contains(5)) {
                    res = "档案上传成功！";
                } else {
                    res = "您上传的档案已提交至管理员处审核！";
                }
                return new ResultMsg<>(true, res, 200, true);
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "获取纸张详情", notes = "获取纸张详情")
    @PostMapping("/getArchiveDetail")
    @Transactional
    public ResultMsg<archiveController.archiveDetail> getArchiveDetail(@RequestBody ID data) {
        try {
            //拿到token进行解析，拿到当前登录用户信息，返回即可
            String token_id = getHttpServletRequest().getHeader("Authorization");
            if (token_id == null || token_id.isEmpty()) {
                return new ResultMsg<>(true, "登录已失效，请重新登录！", 200, null);
            } else {
                archiveController.archiveDetail res = new archiveController.archiveDetail();
                res.archiveItem = archiveMapper.selectOneArchiveItem(data.getId());
                res.archivePaperItem=archiveMapper.selectArchivePaperItem(data.getId());
                res.pictureList=pictureMapper.selectTypePictureList(data.getId(),0);
                res.recordingItem=recordingMapper.selectrecordingList(data.getId(),0);
                res.archiveComponentItem=archiveComponentMapper.selectArchiveComponentItem(data.getId(),0);
                res.commentList=commentMapper.selectTypeCommentList(data.getId(),3);

                return new ResultMsg<>(true, "获取成功", 200, res);
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
    class archiveDetail {
        private archiveItem archiveItem;
        private archivePaperItem archivePaperItem;
        private List<pictureItem> pictureList;
        private recordingItem recordingItem;
        private archiveComponentItem archiveComponentItem;
        private List<commentItem> commentList;

    }
}
