package com.shxy.sudatqd.mapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.shxy.models.dbBean.*;
import com.shxy.models.views.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

@Mapper
public interface institutionMapper extends BaseMapper<institution> {
    List<institutionItem> selectInstitutionItems(@Param("institutionName") String institutionName,
                                                 @Param("institutionLocation") String institutionLocation,
                                                 @Param("startDate") Date startDate,
                                                 @Param("endDate") Date endDate,
                                                 @Param("institutionDescription") String institutionDescription,
                                                 @Param("userName") String userName,
                                                 @Param("state") Integer state,
                                                 @Param("orderColumn") String orderColumn,
                                                 @Param("isAsc") boolean isAsc);
    institutionItem selectOneInstitutionItem(@Param("institutionId") Integer institutionId);
    String sqlpage1 = " SELECT picture.pictureLocation,  institution.* FROM institution INNER JOIN picture ON " +
            " institution.institutionId = picture.objectId WHERE picture.objectType = 2 AND" +
            " picture.homePage = 0 LIMIT 16";

    @Select(sqlpage1)
    List<institutionItem> listHome(@Param(Constants.WRAPPER) Wrapper wrapper);//@Param("ew") Wrapper querywrapper
    List<archiveItem> selectInstitutionItem(@Param("institutionId") Integer institutionId);

}
