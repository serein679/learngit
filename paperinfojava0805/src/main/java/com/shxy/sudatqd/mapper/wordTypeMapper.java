package com.shxy.sudatqd.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shxy.models.dbBean.wordType;
import com.shxy.models.views.wordItem;
import com.shxy.models.views.wordTypeItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface wordTypeMapper extends BaseMapper<wordType> {
    List<wordTypeItem> selectWordTypeItems(@Param("typeName") String typeName,
                                           @Param("description") String description,
                                           @Param("userName") String userName,
                                           @Param("state") Integer state,
                                           @Param("orderColumn") String orderColumn,
                                           @Param("isAsc") boolean isAsc);


}
