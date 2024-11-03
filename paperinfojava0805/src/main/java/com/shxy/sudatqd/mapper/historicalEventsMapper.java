package com.shxy.sudatqd.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shxy.models.dbBean.*;
import com.shxy.models.views.componentItem;
import com.shxy.models.views.fiberItem;
import com.shxy.models.views.historyItem;
import com.shxy.models.views.institutionItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface historicalEventsMapper extends BaseMapper<historicalEvents>{
    List<historyItem> selectHistoryItems(@Param("eventName") String eventName,
                                         @Param("eventDate") Date eventDate,
                                         @Param("eventDescription") String eventDescription,
                                         @Param("eventPic") String eventPic,
                                         @Param("userName") String userName,
                                         @Param("state") Integer state,
                                         @Param("orderColumn") String orderColumn,
                                         @Param("isAsc") boolean isAsc);

    historyItem selectOneHistoryItem(@Param("eventId") Integer eventId);
}
