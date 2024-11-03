package com.shxy.sudatqd.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shxy.models.dbBean.*;
import com.shxy.models.views.paperItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface userMapper extends BaseMapper<user> {
    List<user> selectUsers(@Param("userName") String userName,
                                     @Param("state") Integer state,
                                     @Param("orderColumn") String orderColumn,
                                     @Param("isAsc") boolean isAsc);

}
