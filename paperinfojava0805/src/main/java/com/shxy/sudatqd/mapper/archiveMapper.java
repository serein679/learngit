package com.shxy.sudatqd.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.shxy.models.dbBean.*;
import com.shxy.models.views.archiveHome;
import com.shxy.models.views.archiveItem;
import org.apache.ibatis.annotations.Mapper;
import com.shxy.models.views.archivePaperItem;
import com.shxy.models.views.paperItem;
import lombok.Data;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import org.apache.ibatis.annotations.Param;

//import java.sql.Wrapper;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface archiveMapper extends BaseMapper<archive> {

    List<archiveItem> selectArchiveItems(@Param("archiveName") String archiveName,
                                         @Param("institutionName") String institutionName,
                                         @Param("archiveType") Integer archiveType,
                                         @Param("archiveContent") String archiveContent,
                                         @Param("userName") String userName,
                                         @Param("state") Integer state,
                                         @Param("orderColumn") String orderColumn,
                                         @Param("datefilter") Date datefilter,
                                         @Param("datefilter1") Date datefilter1,
                                         @Param("isAsc") boolean isAsc);
    archiveItem selectOneArchiveItem(@Param("archiveId") Integer archiveId);
    archivePaperItem selectArchivePaperItem(@Param("archiveId") Integer archiveId);

    String sqlpage1 = "SELECT `archive`.archiveId,  `archive`.archiveName,  `paper`.paperId,  `paper`.paperName,  `archive`.arc" +
            "hiveContent," +
            "  `picture`.pictureLocation FROM `archive` INNER JOIN `archivepaper` ON  `archive`.archiveId = `archivepaper`.archiv" +
            "eId INNER JOIN `paper` ON  `archivepaper`.paperId = `paper`.paperId INNER JOIN `picture` ON  `archive`.archiveId = pictu" +
            "re.objectId  WHERE  `picture`.objectType = 0 AND `picture`.homePage = 0";

    @Select(sqlpage1)
    List<archiveHome> listHome(@Param(Constants.WRAPPER) Wrapper wrapper);//@Param("ew") Wrapper querywrapper

}
