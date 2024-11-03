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
public class picture {
    @TableId(value = "pictureId", type = IdType.AUTO)
    private Integer pictureId;
    private Integer objectId;
    private Integer objectType;
    private String pictureName;
    private String pictureLocation;
    private Integer state;
    private Integer homePage;
    private Integer creatorId;
    private Date creationTime;
}
