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
import com.shxy.models.paramsBean.component.addComponentParam;
import com.shxy.models.paramsBean.component.componentQuery;
import com.shxy.models.paramsBean.component.editComponentParam;
import com.shxy.models.paramsBean.fiber.addFiberParam;
import com.shxy.models.paramsBean.user.changeStatus;
import com.shxy.models.paramsBean.user.editPermissionParam;
import com.shxy.models.returnBean.ResultMsg;
import com.shxy.models.views.componentItem;
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
@RequestMapping("/Event")
public class eventsController {
    @Autowired
    private userService userService;
    @Autowired
    private fiberService fiberService;
    @Autowired
    private pictureService pictureService;
    @Value("${jwtSign}")
    String jwtSign;

    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

}
