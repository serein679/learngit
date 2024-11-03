package com.shxy.sudatqd.dbService;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.shxy.models.dbBean.historicalEvents;
import com.shxy.models.paramsBean.historicalEvents.historyQuery;
import com.shxy.models.views.historyItem;
import com.shxy.models.views.institutionItem;
import org.springframework.stereotype.Service;

@Service
public interface historicalEventsService extends IService<historicalEvents>{
    PageInfo<historyItem> historyPage(historyQuery historyQuery);
}
