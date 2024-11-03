package com.shxy.models.paramsBean.paper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class editPaperParam {
    private Integer paperId;
    private String paperName;
    private String paperDes;
    private Integer paperType;
    private String location;
    private String process;
    private String technology;
    private String pictureLocation;
    private List<String> fileParam;
//    private List<Integer> archiveIds;
}
