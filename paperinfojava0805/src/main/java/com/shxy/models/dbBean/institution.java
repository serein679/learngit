package com.shxy.models.dbBean;


import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class institution {
    @TableId(value = "institutionId", type = IdType.AUTO)
    private Integer institutionId;
    private String institutionName;
    private String institutionLocation;
    private String longitude;
    private String latitude;
    private Date institutionDate;
    private String institutionDescription;
    private Integer state;
    private Integer views;
    private Integer creatorId;
    private Date creationTime;
}
