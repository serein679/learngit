package com.shxy.models.paramsBean.historicalEvents;

import com.shxy.models.paramsBean.BasicQueryParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class historyQuery extends BasicQueryParam {
    private String eventName;
    private String eventDescription;
    private Date eventDate;
    private String eventPic;
    private Integer state;
    private Integer views;
    private Date creationTime;
    private String creator;
}
