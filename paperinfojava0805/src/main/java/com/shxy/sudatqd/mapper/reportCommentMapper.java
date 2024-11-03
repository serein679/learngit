package com.shxy.sudatqd.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shxy.models.dbBean.*;
import com.shxy.models.views.archiveItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface reportCommentMapper extends BaseMapper<reportComment> {

    List<reportComment> reportCommentItemList(

                                         @Param("orderColumn") String orderColumn,

                                         @Param("isAsc") boolean isAsc);


}
