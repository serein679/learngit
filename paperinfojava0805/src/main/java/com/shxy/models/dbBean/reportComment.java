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
public class reportComment {
    @TableId(value = "reportId", type = IdType.AUTO)
    private Integer reportId;
    private Integer commentId;
    private Integer reportReasonType;
    private Integer reporterId;
    private Integer reportContentType;
    private String content;
    private Date reportTime;
    private Integer reportState;
}
