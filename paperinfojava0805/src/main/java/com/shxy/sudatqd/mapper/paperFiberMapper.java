package com.shxy.sudatqd.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shxy.models.dbBean.*;
import com.shxy.models.views.fiberItem;
import com.shxy.models.views.paperFiberItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface paperFiberMapper extends BaseMapper<paperFiber> {
    List<paperFiberItem> selectPaperFiberItem(@Param("id") Integer id,
                                                       @Param("type") Integer type);


}
