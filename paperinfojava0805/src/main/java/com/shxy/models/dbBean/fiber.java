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
public class fiber {
    @TableId(value = "fiberId", type = IdType.AUTO)
    private Integer fiberId;
    private String fiberType;
    private String characteristic;
    private Integer views;
    private Integer state;
    private Integer creatorId;
    private Date creationTime;
}
