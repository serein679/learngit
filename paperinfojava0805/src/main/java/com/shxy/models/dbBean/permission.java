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
public class permission {
    @TableId(value = "permissionId", type = IdType.AUTO)
    private Integer permissionId;
    private String permissionName;
    private String description;
    private Integer creatorId;
    private Date creationTime;
    private Integer state;
}
