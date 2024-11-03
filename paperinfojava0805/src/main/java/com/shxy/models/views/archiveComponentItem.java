package com.shxy.models.views;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class archiveComponentItem {
    private Integer archiveId;
    private Integer componentId;
    private Integer creatorId;
    private Date creationTime;
    private Integer status;
    private String componentName;
    private String archiveName;
    private Integer componentType;
    private Integer state;
    private String description;
}
