package com.shxy.models.views;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class archiveHome {
    private Integer archiveId;
    private String archiveName;
    private String archiveContent;
    private String paperName;
    private Integer paperId;
    private String pictureLocation;
}
