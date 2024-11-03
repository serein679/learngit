package com.shxy.sudatqd.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shxy.models.dbBean.*;
import com.shxy.models.views.operation.replyItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface replyMapper extends BaseMapper<reply> {
    List<replyItem> selectTypeReplyList(@Param("id") Integer id,
                                            @Param("type") Integer type);



}
