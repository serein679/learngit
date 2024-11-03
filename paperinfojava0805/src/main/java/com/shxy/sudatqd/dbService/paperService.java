package com.shxy.sudatqd.dbService;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.shxy.models.dbBean.paper;

import com.shxy.models.paramsBean.paper.addPaperParam;
import com.shxy.models.paramsBean.paper.paperQuery;
import com.shxy.models.paramsBean.word.wordQuery;
import com.shxy.models.views.archiveHome;
import com.shxy.models.views.paperItem;
import com.shxy.models.views.wordItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface paperService extends IService<paper> {
    Integer addPaper(addPaperParam paperData, int oneId, DateTime now);
    PageInfo<paperItem> paperPage(paperQuery paperQuery);
    List<paperItem> listHome(QueryWrapper<paperItem> wrapper);
}
