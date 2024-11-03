package com.shxy.sudatqd.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shxy.models.dbBean.*;
import com.shxy.models.views.archiveComponentItem;
import com.shxy.models.views.archivePaperItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface archiveComponentMapper extends BaseMapper<archiveComponent> {
    archiveComponentItem selectArchiveComponentItem(@Param("id") Integer id,
                                                    @Param("type") Integer type);
    List<archiveComponentItem> selectArchiveComponentList(@Param("id") Integer id,
                                                      @Param("type") Integer type);

}
