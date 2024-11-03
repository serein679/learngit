package com.shxy.sudatqd.dbService.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shxy.models.dbBean.wordType;
import com.shxy.models.paramsBean.word.wordQuery;
import com.shxy.models.paramsBean.word.wordTypeQuery;
import com.shxy.models.views.wordItem;
import com.shxy.models.views.wordTypeItem;
import com.shxy.sudatqd.dbService.wordTypeService;
import com.shxy.sudatqd.mapper.wordTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class wordTypeServiceImpl extends ServiceImpl<wordTypeMapper, wordType> implements wordTypeService {
    @Autowired
    private wordTypeMapper wordTypeMapper;

    @Override
    public PageInfo<wordTypeItem> wordTypePage(wordTypeQuery query) {
        // 使用PageHelper进行分页
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<wordTypeItem> wordTypeList = wordTypeMapper.selectWordTypeItems(
                query.getTypeName(),
                query.getDescription(),
                query.getCreator(),
                query.getState(),
                query.getOrderColumn(),
                query.getIsAsc());
        // 获取分页后的结果
        PageInfo<wordTypeItem> pageInfo = new PageInfo<>(wordTypeList);
        return pageInfo;
    }
}
