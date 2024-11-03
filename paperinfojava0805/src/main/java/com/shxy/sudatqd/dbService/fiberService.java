package com.shxy.sudatqd.dbService;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.shxy.models.dbBean.fiber;

import com.shxy.models.paramsBean.component.componentQuery;
import com.shxy.models.paramsBean.fiber.addFiberParam;
import com.shxy.models.paramsBean.fiber.fiberQuery;
import com.shxy.models.paramsBean.paper.addPaperParam;
import com.shxy.models.views.componentItem;
import com.shxy.models.views.fiberItem;
import org.springframework.stereotype.Service;

@Service
public interface fiberService extends IService<fiber> {
    Integer addFiber(addFiberParam fiberData, int oneId, DateTime now);
    PageInfo<fiberItem> fiberPage(fiberQuery fiberQuery);
}
