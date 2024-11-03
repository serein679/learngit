package com.shxy.models.dbBean;


import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class archive {
    @TableId(value = "archiveId", type = IdType.AUTO)
    private Integer archiveId;
    private String archiveName;
    private Integer institutionId;
    private Integer archiveType;
    private String archiveContent;
    private Integer views;
    private Integer archiveStateType;
    private Integer creatorId;
    private Date creationTime;
    private Date firstTime;
}
