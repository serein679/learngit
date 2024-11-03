package com.shxy.sudatqd.dbService.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shxy.models.dbBean.*;
import com.shxy.models.paramsBean.historicalEvents.historyQuery;

import com.shxy.models.views.historyItem;
import com.shxy.sudatqd.dbService.*;
import com.shxy.sudatqd.mapper.historicalEventsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class historicalEventsServiceImpl extends ServiceImpl<historicalEventsMapper, historicalEvents> implements historicalEventsService{
    @Autowired
    private historicalEventsMapper historicalEventsMapper;

    @Override
    public PageInfo<historyItem> historyPage(historyQuery query) {
        // 使用PageHelper进行分页
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<historyItem> historyItemList = historicalEventsMapper.selectHistoryItems(
                query.getEventName(),
                query.getEventDate(),
                query.getEventDescription(),
                query.getEventPic(),
                query.getCreator(),
                query.getState(),
                query.getOrderColumn(),
                query.getIsAsc());
        // 获取分页后的结果
        PageInfo<historyItem> pageInfo = new PageInfo<>(historyItemList);
        return pageInfo;
    }
}
