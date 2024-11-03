package com.shxy.sudatqd.controllers;

import cn.hutool.core.date.DateTime;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageInfo;
import com.shxy.models.dbBean.fiber;
import com.shxy.models.dbBean.user;
import com.shxy.models.paramsBean.ID;
import com.shxy.models.paramsBean.component.componentQuery;
import com.shxy.models.paramsBean.fiber.addFiberParam;
import com.shxy.models.paramsBean.fiber.fiberQuery;
import com.shxy.models.paramsBean.user.changeStatus;
import com.shxy.models.paramsBean.user.editPermissionParam;
import com.shxy.models.returnBean.ResultMsg;
import com.shxy.models.views.*;
import com.shxy.models.views.operation.commentItem;
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
@RequestMapping("/Fiber")
public class fiberController {
    @Autowired
    private userService userService;
    @Autowired
    private com.shxy.sudatqd.mapper.pictureMapper pictureMapper;
    @Autowired
    private com.shxy.sudatqd.mapper.fiberMapper fiberMapper;
    @Autowired
    private com.shxy.sudatqd.mapper.paperFiberMapper paperFiberMapper;
    @Autowired
    private fiberService fiberService;
    @Autowired
    private pictureService pictureService;
    @Value("${jwtSign}")
    String jwtSign;

    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    @ApiOperation(value = "获取纤维列表",notes = "获取纤维列表")
    @PostMapping("/getFiberList")
    @Transactional
    public ResultMsg<List<fiber>> getFiberList() {
        try {
            QueryWrapper<fiber> list = new QueryWrapper<>();
            list.eq("state", 0);
            List<fiber> oneList = fiberService.list(list);
            return new ResultMsg<>(true, "获取成功", 200, oneList);
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "获取纤维列表",notes = "获取纤维列表")
    @PostMapping("/getFiberLists")
    @Transactional
    public ResultMsg<List<fiber>> getFiberLists() {
        try {
            QueryWrapper<fiber> list = new QueryWrapper<>();
            list.eq("state", 0)
                    .last("LIMIT 12");
            List<fiber> oneList = fiberService.list(list);
            return new ResultMsg<>(true, "获取成功", 200, oneList);
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }
    @ApiOperation(value = "获取纤维详情",notes = "获取纤维详情")
    @PostMapping("/getFiberDetail")
    @Transactional
    public ResultMsg<fiberController.fiberDetail> getFiberDetail(@RequestBody ID data) {
        try {
            //拿到token进行解析，拿到当前登录用户信息，返回即可
            String token_id = getHttpServletRequest().getHeader("Authorization");
            if (token_id == null || token_id.isEmpty()) {
                return new ResultMsg<>(true, "登录已失效，请重新登录！", 200, null);
            } else {
                fiberController.fiberDetail res = new fiberController.fiberDetail();
                res.fiberItem=fiberMapper.selectOneFiberItem(data.getId());
                res.paperFiberList=paperFiberMapper.selectPaperFiberItem(data.getId(),0);
                res.pictureList=pictureMapper.selectTypePictureList(data.getId(),4);
                return new ResultMsg<>(true, "获取成功", 200, res);
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }
    @ApiOperation(value = "编辑纤维基本信息",notes = "编辑纤维基本信息")
    @PostMapping("/editFiber")
    @Transactional
    public ResultMsg<Boolean> editFiber(@RequestBody editPermissionParam data) {
        try {
            //拿到token进行解析，拿到当前登录用户信息，返回即可
            String token_id = getHttpServletRequest().getHeader("Authorization");
            if (token_id == null || token_id.isEmpty()) {
                return new ResultMsg<>(true, "登录已失效，请重新登录！", 200, null);
            } else {
                UpdateWrapper<fiber> fiberUpdateWrapper = new UpdateWrapper<>();
                fiberUpdateWrapper.eq("fiberId",data.getId());
                fiberUpdateWrapper.set("characteristic", data.getDescription());
                boolean update = fiberService.update(fiberUpdateWrapper);
                if (update) {
                    return new ResultMsg<>(true, "纤维信息已更新！", 200, update);
                } else {
                    return new ResultMsg<>(true, "操作失败，请重试", 205, null);
                }
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "编辑纤维状态",notes = "编辑纤维状态")
    @PostMapping("/changeFiberStatus")
    @Transactional
    public ResultMsg<Boolean> changeFiberStatus(@RequestBody changeStatus data) {
        try {
            //拿到token进行解析，拿到当前登录用户信息，返回即可
            String token_id = getHttpServletRequest().getHeader("Authorization");
            if (token_id == null || token_id.isEmpty()) {
                return new ResultMsg<>(true, "登录已失效，请重新登录！", 200, null);
            } else {
                UpdateWrapper<fiber> fiberUpdateWrapper = new UpdateWrapper<>();
                fiberUpdateWrapper.eq("fiberId",data.getId());
                fiberUpdateWrapper
                        .set("state", data.getStatus());
                boolean update = fiberService.update(fiberUpdateWrapper);
                if (update) {
                    return new ResultMsg<>(true, "纤维状态已更新！", 200, update);
                } else {
                    return new ResultMsg<>(true, "操作失败，请重试", 205, null);
                }
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "新增纤维",notes = "新增纤维")
    @PostMapping("/addFiber")
    @Transactional
    public ResultMsg<Integer> addFiber(@RequestBody addFiberParam data) {
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
                //新增纤维
                Integer fiberId=fiberService.addFiber(data, oneId, DateTime.now());
                //向图片表中添加信息
//                pictureService.addDefaultPicture( 3,fiberId ,oneId);
                pictureService.addTestPicture(data.getFileParam(),3,fiberId,oneId);
                return new ResultMsg<>(true, "添加纤维成功！", 200, fiberId);
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "获取纤维分页查询列表",notes = "获取纤维分页查询列表")
    @PostMapping("/getFiberPageList")
    @Transactional
    public ResultMsg<PageInfo<fiberItem>> getFiberPageList(@RequestBody fiberQuery data) {
        try {
            //拿到token进行解析，拿到当前登录用户信息，返回即可
            String token_id = getHttpServletRequest().getHeader("Authorization");
            if (token_id == null || token_id.isEmpty()) {
                return new ResultMsg<>(true, "登录已失效，请重新登录！", 200, null);
            } else {
                PageInfo<fiberItem> pageList= fiberService.fiberPage(data);
                return new ResultMsg<>(true, "获取成功", 200, pageList);
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
    class fiberDetail {
        private com.shxy.models.views.fiberItem fiberItem;
        private List<pictureItem> pictureList;
        private List<paperFiberItem> paperFiberList;

//        private recordingItem recordingItem;
//        private paperFiberItem paperFiberItem;
//        private List<paperItem> paperList;

    }

}
