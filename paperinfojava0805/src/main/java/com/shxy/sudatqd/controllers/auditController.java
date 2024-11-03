package com.shxy.sudatqd.controllers;

import cn.hutool.core.date.DateTime;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageInfo;
import com.shxy.models.dbBean.audit;
import com.shxy.models.dbBean.component;
import com.shxy.models.dbBean.user;
import com.shxy.models.paramsBean.component.componentQuery;
import com.shxy.models.paramsBean.operation.auditQuery;
import com.shxy.models.paramsBean.operation.giveAuditParam;
import com.shxy.models.paramsBean.user.changeStatus;
import com.shxy.models.returnBean.ResultMsg;
import com.shxy.models.views.componentItem;
import com.shxy.models.views.fiberItem;
import com.shxy.models.views.operation.auditItem;
import com.shxy.sudatqd.dbService.*;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/Audit")
public class auditController {
    @Autowired
    private userService userService;
    @Autowired
    private auditService auditService;
    @Autowired
    private pictureService pictureService;
    @Value("${jwtSign}")
    String jwtSign;

    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    @ApiOperation(value = "获取审核列表",notes = "获取审核列表")
    @PostMapping("/getAuditList")
    @Transactional
    public ResultMsg<List<audit>> getAuditList() {
        try {
            QueryWrapper<audit> list = new QueryWrapper<>();
            List<audit> oneList = auditService.list(list);
            return new ResultMsg<>(true, "获取成功", 200, oneList);

        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "获取审核分页查询列表",notes = "获取审核分页查询列表")
    @PostMapping("/getAuditPageList")
    @Transactional
    public ResultMsg<PageInfo<auditItem>> getAuditPageList(@RequestBody auditQuery data) {
        try {
            //拿到token进行解析，拿到当前登录用户信息，返回即可
            String token_id = getHttpServletRequest().getHeader("Authorization");
            if (token_id == null || token_id.isEmpty()) {
                return new ResultMsg<>(true, "登录已失效，请重新登录！", 200, null);
            } else {
                System.out.println(data);
                PageInfo<auditItem> pageList= auditService.auditPage(data);
                System.out.println(pageList);
                return new ResultMsg<>(true, "获取成功", 200, pageList);
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "审核",notes = "审核")
    @PostMapping("/audit")
    @Transactional
    public ResultMsg<Boolean> audit(@RequestBody giveAuditParam data) {
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
                //进行审核
                auditService.giveAudit(data.getAuditId(),data.getStatus(),data.getContent(),oneId);
                return new ResultMsg<>(true, "success", 200, true);
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }
//
    //解析当前登录的用户名
    public String getUsernameFromToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(jwtSign);
        DecodedJWT decodedJWT = JWT.require(algorithm)
                .build()
                .verify(token);
        return decodedJWT.getClaim("username").asString();
    }

}
