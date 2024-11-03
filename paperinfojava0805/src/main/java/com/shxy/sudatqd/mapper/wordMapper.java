package com.shxy.sudatqd.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shxy.models.dbBean.word;
import com.shxy.models.views.fiberItem;
import com.shxy.models.views.wordItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface wordMapper extends BaseMapper<word> {

    List<wordItem> selectWordItems(@Param("wordName") String wordName,
                                   @Param("type") Integer type,
                                   @Param("userName") String userName,
                                   @Param("state") Integer state,
                                   @Param("orderColumn") String orderColumn,
                                   @Param("isAsc") boolean isAsc);
}
