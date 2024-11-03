package com.shxy.sudatqd.dbService;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shxy.models.dbBean.archive;
import com.shxy.models.dbBean.archivePaper;
import com.shxy.models.views.archivePaperItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface archivePaperService extends IService<archivePaper> {
    void addArchivePaper(
            Integer archiveId,
            Integer paperId,
            String ph,
            String size,
            String thickness,
            String whiteness,
            String color,
            Integer hengWen,
            String band,
            Integer zongWen,
            String zong,
            Integer shuLiang,
            Integer numbers,
            Integer distribution,
            String lianWen,
            String beatDeg,
            String degFiber,
            Integer creatorId,
            Integer status
    );


}
