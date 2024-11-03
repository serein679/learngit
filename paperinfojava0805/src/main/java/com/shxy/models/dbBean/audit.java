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
public class audit {
    @TableId(value = "auditId", type = IdType.AUTO)
    private Integer auditId;
    private Integer targetId;
    private Integer type;
    private String reviewComments;
    private Integer state;
    private Integer creatorId;
    private Date creationTime;

}
