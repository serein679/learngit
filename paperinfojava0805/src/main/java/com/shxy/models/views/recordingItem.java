package com.shxy.models.views;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class recordingItem {


    private Integer archiveId;
    private String bianHao;
    private String archiveName;
    private String time;
    private String author;
    private String language;
    private String classification;
    private String reference;
    private Integer creatorId;
    private Date creationTime;
    private Integer status;
}
