package com.shxy.sudatqd.controllers;

import cn.hutool.core.date.DateTime;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageInfo;
import com.shxy.models.dbBean.component;
import com.shxy.models.dbBean.fiber;
import com.shxy.models.dbBean.user;
import com.shxy.models.paramsBean.ID;
import com.shxy.models.paramsBean.component.addComponentParam;
import com.shxy.models.paramsBean.component.componentQuery;
import com.shxy.models.paramsBean.component.editComponentParam;
import com.shxy.models.paramsBean.fiber.addFiberParam;
import com.shxy.models.paramsBean.user.changeStatus;
import com.shxy.models.paramsBean.user.editPermissionParam;
import com.shxy.models.returnBean.ResultMsg;
import com.shxy.models.views.archiveComponentItem;
import com.shxy.models.views.componentItem;
import com.shxy.models.views.paperFiberItem;
import com.shxy.models.views.pictureItem;
import com.shxy.sudatqd.dbService.*;
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
@RequestMapping("/Component")
public class componentController {
    @Autowired
    private userService userService;
    @Autowired
    private com.shxy.sudatqd.mapper.componentMapper componentMapper;
    @Autowired
    private com.shxy.sudatqd.mapper.pictureMapper pictureMapper;
    @Autowired
    private com.shxy.sudatqd.mapper.archiveComponentMapper archiveComponentMapper;
    @Autowired
    private componentService componentService;
    @Autowired
    private pictureService pictureService;
    @Value("${jwtSign}")
    String jwtSign;

    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    @ApiOperation(value = "获取成分列表",notes = "获取成分列表")
    @PostMapping("/getComponentList")
    @Transactional
    public ResultMsg<List<component>> getComponentList() {
        try {
            QueryWrapper<component> list = new QueryWrapper<>();
            list.eq("state", 0);
            List<component> oneList = componentService.list(list);
            return new ResultMsg<>(true, "获取成功", 200, oneList);

        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "编辑成分基本信息",notes = "编辑成分基本信息")
    @PostMapping("/editComponent")
    @Transactional
    public ResultMsg<Boolean> editComponent(@RequestBody editComponentParam data) {
        try {
            //拿到token进行解析，拿到当前登录用户信息，返回即可
            String token_id = getHttpServletRequest().getHeader("Authorization");
            if (token_id == null || token_id.isEmpty()) {
                return new ResultMsg<>(true, "登录已失效，请重新登录！", 200, null);
            } else {
                UpdateWrapper<component> componentUpdateWrapper = new UpdateWrapper<>();
                componentUpdateWrapper.eq("componentId",data.getComponentId());
                componentUpdateWrapper.set("componentType", data.getComponentType());
                componentUpdateWrapper.set("description", data.getDescription());
                boolean update = componentService.update(componentUpdateWrapper);
                if (update) {
                    return new ResultMsg<>(true, "成分信息已更新！", 200, update);
                } else {
                    return new ResultMsg<>(true, "操作失败，请重试", 205, null);
                }
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "编辑成分状态",notes = "编辑成分状态")
    @PostMapping("/changeComponentStatus")
    @Transactional
    public ResultMsg<Boolean> changeComponentStatus(@RequestBody changeStatus data) {
        try {
            //拿到token进行解析，拿到当前登录用户信息，返回即可
            String token_id = getHttpServletRequest().getHeader("Authorization");
            if (token_id == null || token_id.isEmpty()) {
                return new ResultMsg<>(true, "登录已失效，请重新登录！", 200, null);
            } else {
                UpdateWrapper<component> componentUpdateWrapper = new UpdateWrapper<>();
                componentUpdateWrapper.eq("componentId",data.getId());
                componentUpdateWrapper
                        .set("state", data.getStatus());
                boolean update = componentService.update(componentUpdateWrapper);
                if (update) {
                    return new ResultMsg<>(true, "成分状态已更新！", 200, update);
                } else {
                    return new ResultMsg<>(true, "操作失败，请重试", 205, null);
                }
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "新增成分",notes = "新增成分")
    @PostMapping("/addComponent")
    @Transactional
    public ResultMsg<Integer> addComponent (@RequestBody addComponentParam data) {
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
                Integer componentId=componentService.addComponent(data, oneId, DateTime.now());
                //向图片表中添加信息
                pictureService.addTestPicture( data.getFileParam(),4,componentId ,oneId);
//                pictureService.addDefaultPicture( 3,componentId ,oneId);
                return new ResultMsg<>(true, "添加成分成功！", 200, componentId);
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "获取成分分页查询列表",notes = "获取成分分页查询列表")
    @PostMapping("/getComponentPageList")
    @Transactional
    public ResultMsg<PageInfo<componentItem>> getComponentPageList(@RequestBody componentQuery data) {
        try {
            //拿到token进行解析，拿到当前登录用户信息，返回即可
            String token_id = getHttpServletRequest().getHeader("Authorization");
            if (token_id == null || token_id.isEmpty()) {
                return new ResultMsg<>(true, "登录已失效，请重新登录！", 200, null);
            } else {
                PageInfo<componentItem> pageList= componentService.componentPage(data);
                return new ResultMsg<>(true, "获取成功", 200, pageList);
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "获取成分详情",notes = "获取成分详情")
    @PostMapping("/getComponentDetail")
    @Transactional
    public ResultMsg<componentController.componentDetail> getComponentDetail(@RequestBody ID data) {
        try {
            //拿到token进行解析，拿到当前登录用户信息，返回即可
            String token_id = getHttpServletRequest().getHeader("Authorization");
            if (token_id == null || token_id.isEmpty()) {
                return new ResultMsg<>(true, "登录已失效，请重新登录！", 200, null);
            } else {
                componentController.componentDetail res = new componentController.componentDetail();
                res.componentItem=componentMapper.selectOneComponentItem(data.getId());
                res.archiveComponentList=archiveComponentMapper.selectArchiveComponentList(data.getId(),0);
                res.pictureList=pictureMapper.selectTypePictureList(data.getId(),5);
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
    class componentDetail {
        private com.shxy.models.views.componentItem componentItem;
        private List<pictureItem> pictureList;
        private List<archiveComponentItem> archiveComponentList;

//        private recordingItem recordingItem;
//        private paperFiberItem paperFiberItem;
//        private List<paperItem> paperList;

    }
}
