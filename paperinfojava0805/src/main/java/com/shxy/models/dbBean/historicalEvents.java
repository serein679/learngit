package com.shxy.models.dbBean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class historicalEvents {
    @TableId(value = "eventId", type = IdType.AUTO)
    private Integer eventId;
    private String eventName;
    private String eventDescription;
    private Date eventDate;
    private String eventPic;
    private Integer state;
    private Integer views;
    private Integer creatorId;
    private Date creationTime;
}
