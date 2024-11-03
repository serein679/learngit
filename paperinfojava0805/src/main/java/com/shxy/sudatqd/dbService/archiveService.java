package com.shxy.sudatqd.dbService;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.shxy.models.dbBean.archive;
import com.shxy.models.paramsBean.archive.addArchiveParam;
import com.shxy.models.paramsBean.archive.archiveQuery;
import com.shxy.models.views.archiveHome;
import com.shxy.models.views.archiveItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface archiveService extends IService<archive> {
    PageInfo<archiveItem> archivePage(archiveQuery archiveQuery);
    List<archiveHome> listHome(QueryWrapper<archiveHome> wrapper);
    void addArchive(addArchiveParam archiveData, int oneId, DateTime now);
}
