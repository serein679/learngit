package com.shxy.sudatqd.dbService;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.shxy.models.dbBean.word;
import com.shxy.models.paramsBean.fiber.fiberQuery;
import com.shxy.models.paramsBean.word.wordQuery;
import com.shxy.models.views.fiberItem;
import com.shxy.models.views.wordItem;
import org.springframework.stereotype.Service;

@Service
public interface wordService extends IService<word> {
    PageInfo<wordItem> wordPage(wordQuery wordQuery);
}
