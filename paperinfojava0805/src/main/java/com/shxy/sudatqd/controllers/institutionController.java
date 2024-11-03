package com.shxy.sudatqd.controllers;

import cn.hutool.core.date.DateTime;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageInfo;
import com.shxy.models.dbBean.institution;
import com.shxy.models.dbBean.paper;
import com.shxy.models.dbBean.user;
import com.shxy.models.paramsBean.ID;
import com.shxy.models.paramsBean.institution.addInstitutionParam;
import com.shxy.models.paramsBean.institution.editInstitutionParam;
import com.shxy.models.paramsBean.institution.institutionQuery;
import com.shxy.models.paramsBean.paper.addPaperParam;
import com.shxy.models.paramsBean.paper.editPaperParam;
import com.shxy.models.paramsBean.user.changeStatus;
import com.shxy.models.paramsBean.word.wordTypeQuery;
import com.shxy.models.returnBean.ResultMsg;
import com.shxy.models.views.*;
import com.shxy.models.views.operation.commentItem;
import com.shxy.models.views.operation.feedbackItem;
import com.shxy.sudatqd.dbService.*;
import com.shxy.sudatqd.mapper.commentMapper;
import com.shxy.sudatqd.mapper.paperMapper;
import com.shxy.sudatqd.mapper.pictureMapper;
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
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(value = "*", maxAge = 3600) //允许跨域
@RequestMapping("/Institution")
public class institutionController {
    @Autowired
    private userService userService;
    @Autowired
    private institutionService institutionService;
    @Autowired
    private pictureService pictureService;
    @Autowired
    private com.shxy.sudatqd.mapper.commentMapper commentMapper;
    @Autowired
    private com.shxy.sudatqd.mapper.pictureMapper pictureMapper;
    @Autowired
    private com.shxy.sudatqd.mapper.institutionMapper institutionMapper;
    @Value("${jwtSign}")
    String jwtSign;

    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    @ApiOperation(value = "获取机构列表",notes = "获取机构列表")
    @PostMapping("/getInstitutionList")
    @Transactional
    public ResultMsg<List<institution>> getInstitutionList() {
        try {
            QueryWrapper<institution> list = new QueryWrapper<>();
            list.eq("state", 0);
            List<institution> oneList = institutionService.list(list);
            return new ResultMsg<>(true, "获取成功", 200, oneList);
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "获取机构列表",notes = "获取机构列表")
    @PostMapping("/getInstitutionLists")
    @Transactional
    public ResultMsg<List<institutionItem>> getInstitutionLists() {
        try {
            QueryWrapper<institutionItem> list = new QueryWrapper<>();
            List<institutionItem> archiveHome = institutionService.listHome(list);
            return new ResultMsg<>(true, "获取成功", 200, archiveHome);
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }
    @ApiOperation(value = "编辑机构基本信息",notes = "编辑机构基本信息")
    @PostMapping("/editInstitution")
    @Transactional
    public ResultMsg<Boolean> editInstitution(@RequestBody editInstitutionParam data) {
        try {
            //拿到token进行解析，拿到当前登录用户信息，返回即可
            String token_id = getHttpServletRequest().getHeader("Authorization");
            if (token_id == null || token_id.isEmpty()) {
                return new ResultMsg<>(true, "登录已失效，请重新登录！", 200, null);
            } else {
                UpdateWrapper<institution> institutionUpdateWrapper = new UpdateWrapper<>();
                institutionUpdateWrapper.eq("institutionId",data.getInstitutionId());
                institutionUpdateWrapper
                        .set("institutionName", data.getInstitutionName())
                        .set("institutionLocation", data.getInstitutionLocation())
                        .set("longitude", data.getLongitude())
                        .set("latitude", data.getLatitude())
                        .set("institutionDate", data.getInstitutionDate())
                        .set("institutionDescription", data.getInstitutionDescription());
                boolean update = institutionService.update(institutionUpdateWrapper);
                if (update) {
                    return new ResultMsg<>(true, "机构信息已更新！", 200, update);
                } else {
                    return new ResultMsg<>(true, "操作失败，请重试", 205, null);
                }
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "编辑机构状态",notes = "编辑机构状态")
    @PostMapping("/changeInstitutionStatus")
    @Transactional
    public ResultMsg<Boolean> changeInstitutionStatus(@RequestBody changeStatus data) {
        try {
            //拿到token进行解析，拿到当前登录用户信息，返回即可
            String token_id = getHttpServletRequest().getHeader("Authorization");
            if (token_id == null || token_id.isEmpty()) {
                return new ResultMsg<>(true, "登录已失效，请重新登录！", 200, null);
            } else {
                UpdateWrapper<institution> institutionUpdateWrapper = new UpdateWrapper<>();
                institutionUpdateWrapper.eq("institutionId",data.getId());
                institutionUpdateWrapper
                        .set("state", data.getStatus());
                boolean update = institutionService.update(institutionUpdateWrapper);
                if (update) {
                    return new ResultMsg<>(true, "机构状态已更新！", 200, update);
                } else {
                    return new ResultMsg<>(true, "操作失败，请重试", 205, null);
                }
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "新增机构",notes = "新增机构")
    @PostMapping("/addInstitution")
    @Transactional
    public ResultMsg<Integer> addInstitution(@RequestBody addInstitutionParam data) {
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
                //新增机构
                Integer institutionId=institutionService.addInstitution(data, oneId, DateTime.now());
                //向图片表中添加默认信息
                pictureService.addTestPicture( data.getFileParam(),2,institutionId ,oneId);
                //pictureService.addDefaultPicture( 2,institutionId ,oneId);
                return new ResultMsg<>(true, "添加机构成功！", 200, institutionId);
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }
    @ApiOperation(value = "获取机构分页查询列表",notes = "获取机构分页查询列表")
    @PostMapping("/getInstitutionPageList")
    @Transactional
    public ResultMsg<PageInfo<institutionItem>> getInstitutionPageList(@RequestBody institutionQuery data) {
        try {
            //拿到token进行解析，拿到当前登录用户信息，返回即可
            String token_id = getHttpServletRequest().getHeader("Authorization");
            if (token_id == null || token_id.isEmpty()) {
                return new ResultMsg<>(true, "登录已失效，请重新登录！", 200, null);
            } else {
                PageInfo<institutionItem> pageList= institutionService.institutionPage(data);
                return new ResultMsg<>(true, "获取成功", 200, pageList);
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }
    @ApiOperation(value = "获取机构详情",notes = "获取机构详情")
    @PostMapping("/getInstitutionDetail")
    @Transactional
    public ResultMsg<institutionDetail> getInstitutionDetail(@RequestBody ID data) {
        try {
            //拿到token进行解析，拿到当前登录用户信息，返回即可
            String token_id = getHttpServletRequest().getHeader("Authorization");
            if (token_id == null || token_id.isEmpty()) {
                return new ResultMsg<>(true, "登录已失效，请重新登录！", 200, null);
            } else {
                institutionDetail res = new institutionDetail();
                res.institutionItem=institutionMapper.selectOneInstitutionItem(data.getId());
                res.archiveList=institutionMapper.selectInstitutionItem(data.getId());
                res.pictureList=pictureMapper.selectTypePictureList(data.getId(),2);
//                res.feedbackList=feedbackMapper.selectTypeFeedbackList(data.getId(),0);
                res.commentList=commentMapper.selectTypeCommentList(data.getId(),2);

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
    class institutionDetail {
        private institutionItem institutionItem;
//        private List<archivePaperItem> archivePaperList;
        private List<pictureItem> pictureList;
        private List<archiveItem> archiveList;
        private List<commentItem> commentList;

    }
}
