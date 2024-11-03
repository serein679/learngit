package com.shxy.sudatqd.dbService;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.shxy.models.dbBean.component;

import com.shxy.models.paramsBean.component.*;
import com.shxy.models.views.componentItem;
import org.springframework.stereotype.Service;

@Service
public interface componentService extends IService<component> {
    Integer addComponent(addComponentParam componentData, int oneId, DateTime now);

    PageInfo<componentItem> componentPage(componentQuery componentQuery);
}
