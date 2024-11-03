package com.shxy.sudatqd.dbService;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shxy.models.dbBean.paperFiber;

import com.shxy.models.paramsBean.institution.addInstitutionParam;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public interface paperFiberService extends IService<paperFiber> {
    void addPaperFiber(Integer archiveId,
                          Integer fiberId,
                          Float fiberContent,
                          Integer creatorId,
                          Integer status);
}
