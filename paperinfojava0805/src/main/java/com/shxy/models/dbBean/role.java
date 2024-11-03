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
public class role {
    @TableId(value = "roleId", type = IdType.AUTO)
    private Integer roleId;
    private String roleName;
    private String description;
    private Integer creatorId;
    private Date creationTime;
    private Integer state;
}
