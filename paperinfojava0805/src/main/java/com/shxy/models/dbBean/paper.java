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
public class paper {
    @TableId(value = "paperId", type = IdType.AUTO)
    private Integer paperId;
    private String paperName;
    private String paperDes;
    private Integer paperType;
    private Integer state;
    private String location;
    private String process;
    private String technology;
    private Integer views;
    private Integer creatorId;
    private Date creationTime;
}
