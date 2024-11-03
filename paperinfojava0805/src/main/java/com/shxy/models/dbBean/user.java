package com.shxy.models.dbBean;


import com.baomidou.mybatisplus.annotation.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class user {
    @TableId(value = "userId", type = IdType.AUTO)
    private Integer userId;
    private Integer state;
    private String userName;
    private String picture;
    private String userPass;
    private Integer quesId;
    private String answer;
    private Integer userType;


}
