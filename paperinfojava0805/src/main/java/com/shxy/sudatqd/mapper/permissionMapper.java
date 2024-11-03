package com.shxy.sudatqd.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shxy.models.dbBean.*;
import com.shxy.models.views.permissionItem;
import com.shxy.models.views.roleItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface permissionMapper extends BaseMapper<permission> {

    List<permissionItem> selectPermissionItems(@Param("permissionName") String permissionName,
                                               @Param("description") String description,
                                               @Param("userName") String userName,
                                               @Param("state") Integer state,
                                               @Param("orderColumn") String orderColumn,
                                               @Param("isAsc") boolean isAsc);
}
