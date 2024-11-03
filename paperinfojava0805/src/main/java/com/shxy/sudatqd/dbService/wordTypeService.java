package com.shxy.sudatqd.dbService;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.shxy.models.dbBean.wordType;
import com.shxy.models.paramsBean.word.wordQuery;
import com.shxy.models.paramsBean.word.wordTypeQuery;
import com.shxy.models.views.wordItem;
import com.shxy.models.views.wordTypeItem;
import org.springframework.stereotype.Service;

@Service
public interface wordTypeService extends IService<wordType> {
    PageInfo<wordTypeItem> wordTypePage(wordTypeQuery wordQuery);
}
