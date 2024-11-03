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
public class word {
    @TableId(value = "wordId", type = IdType.AUTO)
    private Integer wordId;
    private String wordName;
    private Integer type;
    private Integer state;
    private Integer creatorId;
    private Date creationTime;
}
