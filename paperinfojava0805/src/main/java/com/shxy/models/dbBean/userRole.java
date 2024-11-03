package com.shxy.models.dbBean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class userRole {
    private Integer userId;
    private Integer roleId;
    private Integer creatorId;
    private Date creationTime;
}
