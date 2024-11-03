package com.shxy.sudatqd.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shxy.models.dbBean.*;
import com.shxy.models.views.operation.commentItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface commentMapper extends BaseMapper<comment> {
    List<commentItem> selectTypeCommentList(@Param("id") Integer id,
                                              @Param("type") Integer type);


}
