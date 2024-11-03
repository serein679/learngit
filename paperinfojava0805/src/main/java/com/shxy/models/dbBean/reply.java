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
public class reply {
    @TableId(value = "replyId", type = IdType.AUTO)
    private Integer replyId;
    private Integer userId;
    private Integer replyyId;
    private Integer type;
    private String replyContent;
    private Date replyTime;
    private Integer replyState;
}
