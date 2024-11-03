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
public class archivePaper {
    private Integer archiveId;
    private Integer paperId;
    private String ph;
    private String size;
    private String thickness;
    private String whiteness;
    private String color;
    private Integer hengWen;
    private String band;
    private Integer zongWen;
    private String zong;
    private Integer shuLiang;
    private Integer numbers;
    private Integer distribution;
    private String lianWen;
    private String beatDeg;
    private String degFiber;
    private String form;
    private Integer creatorId;
    private Date creationTime;
    private Integer status;
}
