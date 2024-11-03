package com.shxy.sudatqd.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shxy.models.dbBean.*;
import com.shxy.models.views.operation.feedbackItem;
import com.shxy.models.views.pictureItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface feedbackMapper extends BaseMapper<feedback> {

    List<feedbackItem> selectTypeFeedbackList(@Param("id") Integer id,
                                             @Param("type") Integer type);
}
