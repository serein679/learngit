package com.shxy.sudatqd.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shxy.models.dbBean.*;
import com.shxy.models.views.archivePaperItem;
import com.shxy.models.views.pictureItem;
import com.shxy.models.views.recordingItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface recordingMapper extends BaseMapper<recording> {
    recordingItem selectrecordingList(@Param("id") Integer id,
                                          @Param("type") Integer type);


}
