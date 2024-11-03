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
import com.shxy.models.paramsBean.paper.addPaperParam;
import com.shxy.models.paramsBean.paper.editPaperParam;
import com.shxy.models.paramsBean.paper.paperQuery;
import com.shxy.models.paramsBean.user.changeStatus;
import com.shxy.models.returnBean.ResultMsg;
import com.shxy.models.views.*;
import com.shxy.models.views.operation.commentItem;
import com.shxy.models.views.operation.feedbackItem;
import com.shxy.models.views.operation.replyItem;
import com.shxy.sudatqd.dbService.*;
import com.shxy.sudatqd.mapper.*;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin(value = "*", maxAge = 3600) //允许跨域
@RequestMapping("/Paper")
public class paperController {
    @Autowired
    private userService userService;
    @Autowired
    private paperService paperService;
    @Autowired
    private archivePaperService archivePaperService;
    @Autowired
    private pictureService pictureService;
    @Autowired
    private auditService auditService;
    @Autowired
    private paperMapper paperMapper;
    @Autowired
    private archivePaperMapper archivePaperMapper;
    @Autowired
    private pictureMapper pictureMapper;
    @Autowired
    private feedbackMapper feedbackMapper;
    @Autowired
    private commentMapper commentMapper;
    @Autowired
    private replyMapper replyMapper;
    @Value("${jwtSign}")
    String jwtSign;

    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    @ApiOperation(value = "获取纸张列表",notes = "获取纸张列表")
    @PostMapping("/getPaperList")
    @Transactional
    public ResultMsg<List<paper>> getPaperList() {
        try {
            QueryWrapper<paper> list = new QueryWrapper<>();
            list.eq("state", 0);
            List<paper> oneList = paperService.list(list);
            return new ResultMsg<>(true, "获取成功", 200, oneList);
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }
    @ApiOperation(value = "获取纸张列表",notes = "获取纸张列表")
    @PostMapping("/getPaperTime")
    @Transactional
    public ResultMsg<List<paper>> getPaperTime() {
        try {
            QueryWrapper<paper> list = new QueryWrapper<>();
            list.last("LIMIT 12");
            List<paper> oneList = paperService.list(list);
            return new ResultMsg<>(true, "获取成功", 200, oneList);
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "编辑纸张基本信息",notes = "编辑纸张基本信息")
    @PostMapping("/editPaper")
    @Transactional
    public ResultMsg<Boolean> editPaper(@RequestBody editPaperParam data) {
        try {
            //拿到token进行解析，拿到当前登录用户信息，返回即可
            String token_id = getHttpServletRequest().getHeader("Authorization");
            if (token_id == null || token_id.isEmpty()) {
                return new ResultMsg<>(true, "登录已失效，请重新登录！", 200, null);
            } else {
                UpdateWrapper<paper> paperUpdateWrapper = new UpdateWrapper<>();
                paperUpdateWrapper.eq("paperId",data.getPaperId());
                paperUpdateWrapper
                        .set("paperName", data.getPaperName())
                        .set("paperDes", data.getPaperDes())
                        .set("paperType", data.getPaperType())
                        .set("location", data.getLocation())
                        .set("process", data.getProcess())
//                        .set("pictureLocation", data.getPictureLocation())
                        .set("technology", data.getTechnology());
                // System.out.println(data.getPictureLocation());
                boolean update = paperService.update(paperUpdateWrapper);
                HashMap<String, Object> map = new HashMap<>();
                map.put("paperId",data.getPaperId());
                boolean b = archivePaperService.removeByMap(map);
                System.out.println(b);  // true
                pictureService.delePicture(1,data.getPaperId());
                pictureService.addTestPicture(data.getFileParam(),1,data.getPaperId(),11);
                pictureService.addTestPicture1(data.getPictureLocation(),1,data.getPaperId(),11);



//                List<Integer> archiveIds =data.getArchiveIds();
//                int size1 = archiveIds.size();
//                if (size1 > 0) {
//                    List<archivePaper> archiveIdList = new ArrayList<>(size1);
//                    for (int i = 0; i < archiveIds.size(); i++) {
////                        int value = ((Integer)courseIds).get();
////                        System.out.println(courseIds.size());
//                        System.out.println(archiveIds.get(i));
//                        Integer cou = archiveIds.get(i);
//                        UpdateWrapper<archivePaper> usersUpdateWrapper = new UpdateWrapper<>();
//                        usersUpdateWrapper.eq("archiveId", cou);
//                        usersUpdateWrapper.set("paperId",data.getPaperId());
//                        update = archivePaperService.update(usersUpdateWrapper);
//                    }
//                    System.out.println(archiveIdList);
//                    archivePaperService.saveBatch(archiveIdList);
//                    System.out.println(archiveIdList);
//                }
                if (update) {
                    return new ResultMsg<>(true, "纸张信息已更新！", 200, update);
                } else {
                    return new ResultMsg<>(true, "操作失败，请重试", 205, null);
                }
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "编辑纸张状态",notes = "编辑纸张状态")
    @PostMapping("/changePaperStatus")
    @Transactional
    public ResultMsg<Boolean> changePaperStatus(@RequestBody changeStatus data) {
        try {
            //拿到token进行解析，拿到当前登录用户信息，返回即可
            String token_id = getHttpServletRequest().getHeader("Authorization");
            if (token_id == null || token_id.isEmpty()) {
                return new ResultMsg<>(true, "登录已失效，请重新登录！", 200, null);
            } else {
                UpdateWrapper<paper> paperUpdateWrapper = new UpdateWrapper<>();
                paperUpdateWrapper.eq("paperId",data.getId());
                paperUpdateWrapper
                        .set("state", data.getStatus());
                boolean update = paperService.update(paperUpdateWrapper);
                if (update) {
                    return new ResultMsg<>(true, "纸张状态已更新！", 200, update);
                } else {
                    return new ResultMsg<>(true, "操作失败，请重试", 205, null);
                }
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "新增纸张",notes = "新增纸张")
    @PostMapping("/addPaper")
    @Transactional
    public ResultMsg<Integer> addPaper(@RequestBody addPaperParam data) {
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
                //新增纸张
                Integer paperId=paperService.addPaper(data, oneId, DateTime.now());
                //向图片表中添加信息
//                pictureService.addTestPicture( data.getFileParam(),1,paperId ,oneId);
                pictureService.addTestPicture(data.getFileParam(),1,paperId,11);
                pictureService.addTestPicture1(data.getPictureLocation(),1,paperId,11);
//                pictureService.addDefaultPicture( 1,paperId ,oneId);
                //如果为普通用户添加，修改纸张状态为2待审核，并添加审核表信息
                UpdateWrapper<paper> paperUpdateWrapper = new UpdateWrapper<>();
                paperUpdateWrapper.eq("paperId",paperId);
                paperUpdateWrapper
                        .set("state", 2);
                auditService.addAudit(1,paperId);
                return new ResultMsg<>(true, "添加纸张成功！", 200, paperId);
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "获取纸张分页查询列表",notes = "获取纸张分页查询列表")
    @PostMapping("/getPaperPageList")
    @Transactional
    public ResultMsg<PageInfo<paperItem>> getPaperPageList(@RequestBody paperQuery data) {
        try {
            //拿到token进行解析，拿到当前登录用户信息，返回即可
            String token_id = getHttpServletRequest().getHeader("Authorization");
            if (token_id == null || token_id.isEmpty()) {
                return new ResultMsg<>(true, "登录已失效，请重新登录！", 200, null);
            } else {
                PageInfo<paperItem> pageList= paperService.paperPage(data);
                return new ResultMsg<>(true, "获取成功", 200, pageList);
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "获取档案首页列表", notes = "获取档案首页列表")
    @PostMapping("/getPaperLists")
    @Transactional
    public ResultMsg<List<paperItem>> getPaperLists() {
        try {
            QueryWrapper<paperItem> list = new QueryWrapper<>();
            List<paperItem> archiveHome = paperService.listHome(list);
            return new ResultMsg<>(true, "获取成功", 200, archiveHome);
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }




    @ApiOperation(value = "获取纸张详情",notes = "获取纸张详情")
    @PostMapping("/getPaperDetail")
    @Transactional
    public ResultMsg<paperDetail> getPaperDetail(@RequestBody ID data) {
        try {
            //拿到token进行解析，拿到当前登录用户信息，返回即可
            String token_id = getHttpServletRequest().getHeader("Authorization");
            if (token_id == null || token_id.isEmpty()) {
                return new ResultMsg<>(true, "登录已失效，请重新登录！", 200, null);
            } else {
                paperDetail res = new paperDetail();
                res.paperItem=paperMapper.selectOnePaperItem(data.getId());
                res.archivePaperList=archivePaperMapper.selectArchivePaperItems(data.getId());
                res.pictureList=pictureMapper.selectTypePictureList(data.getId(),1);
                res.feedbackList=feedbackMapper.selectTypeFeedbackList(data.getId(),0);
                res.commentList=commentMapper.selectTypeCommentList(data.getId(),1);

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
    class paperDetail {
        private paperItem paperItem;
        private List<archivePaperItem> archivePaperList;
        private List<pictureItem> pictureList;
        private List<feedbackItem> feedbackList;
        private List<commentItem> commentList;

    }
}
