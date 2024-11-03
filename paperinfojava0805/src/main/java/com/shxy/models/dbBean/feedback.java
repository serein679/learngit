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
public class feedback {
    @TableId(value = "feedbackId", type = IdType.AUTO)
    private Integer feedbackId;
    private Integer userId;
    private Integer objectId;
    private Integer feedbackType;
    private String content;
    private Date creationTime;
    private Integer state;
}
