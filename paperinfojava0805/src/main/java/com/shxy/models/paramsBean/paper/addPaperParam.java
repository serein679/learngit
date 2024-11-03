package com.shxy.models.paramsBean.paper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @description
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class addPaperParam {
    private String paperName;
    private String paperDes;
    private Integer paperType;
    private String location;
    private String process;
    private String technology;
    private String pictureLocation;
//    private List<Integer> archiveIds;
//    private List<Integer> fiberIds;
    private List<String> fileParam;
}
