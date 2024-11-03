package com.shxy.sudatqd.dbService;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.shxy.models.dbBean.audit;

import com.shxy.models.paramsBean.component.addComponentParam;
import com.shxy.models.paramsBean.operation.auditQuery;
import com.shxy.models.paramsBean.paper.paperQuery;
import com.shxy.models.views.operation.auditItem;
import com.shxy.models.views.paperItem;
import org.springframework.stereotype.Service;

@Service
public interface auditService extends IService<audit> {
    Integer addAudit(
            Integer type,
            Integer targetId);

    PageInfo<auditItem> auditPage(auditQuery auditQuery);

    void giveAudit(Integer auditId,Integer status,String content,Integer userId);
}
