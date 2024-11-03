package com.shxy.sudatqd.mapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shxy.models.dbBean.*;
import com.shxy.models.views.componentItem;
import com.shxy.models.views.fiberItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface componentMapper extends BaseMapper<component> {
    List<componentItem> selectComponentItems(@Param("componentName") String componentName,
                                             @Param("componentType") Integer componentType,
                                             @Param("userName") String userName,
                                             @Param("state") Integer state,
                                             @Param("orderColumn") String orderColumn,
                                             @Param("isAsc") boolean isAsc);
    componentItem selectOneComponentItem(@Param("componentId") Integer componentId);
}
