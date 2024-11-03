package com.shxy.sudatqd.controllers;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageInfo;
import com.shxy.models.dbBean.archive;
import com.shxy.models.dbBean.fiber;
import com.shxy.models.dbBean.historicalEvents;
import com.shxy.models.paramsBean.ID;
import com.shxy.models.paramsBean.historicalEvents.historyQuery;
import com.shxy.models.returnBean.ResultMsg;
import com.shxy.models.views.historyItem;
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
@RequestMapping("/HistoricalEvents")
public class hisController {

    @Autowired
    private userService userService;
    @Autowired
    private com.shxy.sudatqd.mapper.historicalEventsMapper historicalEventsMapper;
    @Autowired
    private historicalEventsService historicalEventsService;
    @Autowired
    private archiveService archiveService;
    @Value("${jwtSign}")
    String jwtSign;

    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    @ApiOperation(value = "获取事件列表",notes = "获取历史事件")
    @PostMapping("/getHistoryList")
    @Transactional
    public ResultMsg<List<historicalEvents>> getHistoryList() {
        try {
            QueryWrapper<historicalEvents> list = new QueryWrapper<>();
            list.eq("state", 0)
                    .last("LIMIT 12")
                    .orderByDesc("eventDate");;
            List<historicalEvents> oneList = historicalEventsService.list(list);
            return new ResultMsg<>(true, "获取成功", 200, oneList);
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "获取事件分页查询列表",notes = "获取事件分页查询列表")
    @PostMapping("/PageAllHis")
    @Transactional
    public ResultMsg<PageInfo<historyItem>> PageAllHis(@RequestBody historyQuery data) {
        try {
            //拿到token进行解析，拿到当前登录用户信息，返回即可
            String token_id = getHttpServletRequest().getHeader("Authorization");
            if (token_id == null || token_id.isEmpty()) {
                return new ResultMsg<>(true, "登录已失效，请重新登录！", 200, null);
            } else {
                PageInfo<historyItem> pageList= historicalEventsService.historyPage(data);
                return new ResultMsg<>(true, "获取成功", 200, pageList);
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }
    @ApiOperation(value = "获取机构详情",notes = "获取机构详情")
    @PostMapping("/getOneHistoricalEvent")
    @Transactional
    public ResultMsg<hisController.historyDetail> getOneHistoricalEvent(@RequestBody ID data) {
        try {
            //拿到token进行解析，拿到当前登录用户信息，返回即可
            String token_id = getHttpServletRequest().getHeader("Authorization");
            if (token_id == null || token_id.isEmpty()) {
                return new ResultMsg<>(true, "登录已失效，请重新登录！", 200, null);
            } else {
                hisController.historyDetail res = new hisController.historyDetail();
                res.historyItem=historicalEventsMapper.selectOneHistoryItem(data.getId());
//                res.archiveList=institutionMapper.selectInstitutionItem(data.getId());

                return new ResultMsg<>(true, "获取成功", 200, res);
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class historyDetail {
        private com.shxy.models.views.historyItem historyItem;

    }

}
