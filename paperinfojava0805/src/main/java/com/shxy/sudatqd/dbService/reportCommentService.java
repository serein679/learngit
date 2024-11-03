package com.shxy.sudatqd.dbService;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.shxy.models.dbBean.reportComment;

import com.shxy.models.paramsBean.BasicQueryParam;
import com.shxy.models.paramsBean.word.wordQuery;
import com.shxy.models.views.wordItem;
import org.springframework.stereotype.Service;
@Service
public interface reportCommentService extends IService<reportComment> {
    PageInfo<reportComment> reportCommentItemList(BasicQueryParam data);


}
