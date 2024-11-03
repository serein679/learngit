package com.shxy.sudatqd.dbService.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shxy.models.dbBean.*;
import com.shxy.models.paramsBean.BasicQueryParam;
import com.shxy.models.paramsBean.word.wordQuery;
import com.shxy.models.views.wordItem;
import com.shxy.sudatqd.dbService.*;
import com.shxy.sudatqd.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shxy.models.dbBean.*;
import com.shxy.sudatqd.dbService.*;
import com.shxy.sudatqd.mapper.*;
import org.springframework.stereotype.Component;

import org.springframework.beans.factory.annotation.Autowired;
@Component
public class reportCommentServiceImpl extends ServiceImpl<reportCommentMapper, reportComment> implements reportCommentService {
    @Autowired
    private reportCommentMapper reportCommentMapper;
    @Override
    public PageInfo<reportComment> reportCommentItemList(BasicQueryParam query) {
        // 使用PageHelper进行分页
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<reportComment> wordList = reportCommentMapper.reportCommentItemList(
//                query.getWordName(),
//                query.getType(),
//                query.getCreator(),
//                query.getState(),
                query.getOrderColumn(),
                query.getIsAsc());
        // 获取分页后的结果
        PageInfo<reportComment> pageInfo = new PageInfo<>(wordList);
        return pageInfo;
    }

}
