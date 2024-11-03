package com.shxy.sudatqd.mapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.shxy.models.dbBean.*;
import com.shxy.models.paramsBean.paper.addPaperParam;
import com.shxy.models.views.archiveHome;
import com.shxy.models.views.fiberItem;
import com.shxy.models.views.paperItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface paperMapper extends BaseMapper<paper> {
    List<paperItem> selectPaperItems(@Param("paperName") String paperName,
                                     @Param("paperDes") String paperDes,
                                     @Param("paperType") Integer paperType,
                                     @Param("location") String location,
                                     @Param("process") String process,
                                     @Param("technology") String technology,
                                     @Param("userName") String userName,
                                     @Param("state") Integer state,
                                     @Param("orderColumn") String orderColumn,
                                     @Param("isAsc") boolean isAsc);
    paperItem selectOnePaperItem(@Param("paperId") Integer paperId);



    String sqlpage1 = " SELECT paper.paperId,  paper.paperName,  paper.paperDes,  picture.pictureLocation,  paper.creationTime FROM paper INNER JOIN picture " +
            " ON  paper.paperId = picture.objectId WHERE paper.state = 0 AND picture.homePage = 0 AND picture.objectType = 1 ORDER BY paper.creationTime DESC";

    @Select(sqlpage1)
    List<paperItem> listHome(@Param(Constants.WRAPPER) Wrapper wrapper);//@Param("ew") Wrapper querywrapper

    List<paperItem> selectPaperItem(@Param(Constants.WRAPPER) Wrapper wrapper);//@Param("ew") Wrapper querywrapper
}
