package com.shxy.models.views;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class paperFiberItem {
    private Integer paperId;
    private String paperName;
    private String fiberType;
    private Integer fiberId;
    private Float fiberContent;
    private Integer creatorId;
    private Date creationTime;
    private Integer status;
}
