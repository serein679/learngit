package com.shxy.models.paramsBean.archive;

import com.shxy.models.paramsBean.ID;
import com.shxy.models.paramsBean.idAndText;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class addArchiveParam {
    // 档案信息
    private String archiveName;
    private Integer institutionId;
    private Integer archiveType;
    private String archiveContent;
    // 图片信息
    private List<MultipartFile> fileParam;
    // 著录字段
    private addRecordParam record;
    // 档案纸张纤维
    private List<idAndText> archiveFiberList;// 纤维id和纤维含量[{"id":"1","tex"t:"aa"},{"id":"2","tex"t:"ssa"},{"id":"3","tex"t:"wer"}]
    //档案纸张信息
    private List<addArchivePaperParam> archivePaperList;
    //档案成分信息
    private List<ID> componentList;


}
