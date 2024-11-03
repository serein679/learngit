package com.shxy.sudatqd.dbService.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shxy.models.dbBean.word;
import com.shxy.models.paramsBean.word.wordQuery;
import com.shxy.models.views.wordItem;
import com.shxy.sudatqd.dbService.wordService;
import com.shxy.sudatqd.mapper.wordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class wordServiceImpl extends ServiceImpl<wordMapper, word> implements wordService {
    @Autowired
    private wordMapper wordMapper;

    @Override
    public PageInfo<wordItem> wordPage(wordQuery query) {
        // 使用PageHelper进行分页
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<wordItem> wordList = wordMapper.selectWordItems(
                query.getWordName(),
                query.getType(),
                query.getCreator(),
                query.getState(),
                query.getOrderColumn(),
                query.getIsAsc());
        // 获取分页后的结果
        PageInfo<wordItem> pageInfo = new PageInfo<>(wordList);
        return pageInfo;
    }
}
