package com.shxy.models.views;


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
public class reportComment {
    @TableId(value = "reportId", type = IdType.AUTO)
    private Integer reportId;
    private Integer commentId;
    private Integer reporterId;
    private String content;
    private String creator;
    private Date reportTime;
    private Integer reportReasonType;
    private Integer reportContentType;
    private Integer reportState;
}
