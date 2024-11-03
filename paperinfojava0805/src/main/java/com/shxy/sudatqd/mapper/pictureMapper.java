package com.shxy.sudatqd.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shxy.models.dbBean.*;
import com.shxy.models.paramsBean.paper.addPaperParam;
import com.shxy.models.views.paperItem;
import com.shxy.models.views.pictureItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Mapper
public interface pictureMapper extends BaseMapper<picture> {
    List<pictureItem> selectTypePictureList(@Param("id") Integer id,
                                            @Param("type") Integer type);
}
