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
public class component {
    @TableId(value = "componentId", type = IdType.AUTO)
    private Integer componentId;
    private String componentName;
    private Integer componentType;
    private Integer state;
    private Integer creatorId;
    private Date creationTime;
    private String description;

}
