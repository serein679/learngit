package com.shxy.models.paramsBean.archive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @description
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class editArchiveParam {
    private Integer archiveId;
    private String archiveName;
    private Integer institutionId;
    private Integer archiveType;
    private String archiveContent;
}
