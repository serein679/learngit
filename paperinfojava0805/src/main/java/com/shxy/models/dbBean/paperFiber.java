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
public class paperFiber {
    private Integer paperId;
    private Integer fiberId;
    private Float fiberContent;
    private Integer creatorId;
    private Date creationTime;
    private Integer status;
}
