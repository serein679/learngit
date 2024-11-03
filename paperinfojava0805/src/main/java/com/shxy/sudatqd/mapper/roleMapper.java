package com.shxy.sudatqd.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shxy.models.dbBean.*;
import com.shxy.models.views.paperItem;
import com.shxy.models.views.roleItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface roleMapper extends BaseMapper<role> {

    List<roleItem> selectRoleItems(@Param("roleName") String roleName,
                                   @Param("description") String description,
                                   @Param("userName") String userName,
                                   @Param("state") Integer state,
                                   @Param("orderColumn") String orderColumn,
                                   @Param("isAsc") boolean isAsc);
}
