package com.shxy.sudatqd.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shxy.models.dbBean.*;
import com.shxy.models.views.archivePaperItem;
import com.shxy.models.views.paperItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface archivePaperMapper extends BaseMapper<archivePaper> {

    List<archivePaperItem> selectArchivePaperItems(@Param("paperId") Integer paperId);
}
