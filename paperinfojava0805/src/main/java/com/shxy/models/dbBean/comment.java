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
public class comment {
    @TableId(value = "commentId", type = IdType.AUTO)
    private Integer commentId;
    private Integer userId;
    private Integer objectType;
    private Integer objectId;
    private Integer commentState;
    private Date commentTime;
    private String commentContent;

}
