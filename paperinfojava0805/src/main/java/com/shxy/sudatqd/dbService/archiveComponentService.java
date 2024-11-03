package com.shxy.sudatqd.dbService;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shxy.models.dbBean.archiveComponent;
import org.springframework.stereotype.Service;

@Service
public interface archiveComponentService extends IService<archiveComponent> {
    void addArchiveComponent(
            Integer archiveId,
            Integer componentId,
            Integer creatorId,
            Integer status
    );
}
