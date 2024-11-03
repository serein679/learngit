package com.shxy.models.paramsBean.institution;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

/**
 * @description
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class addInstitutionParam {
    private String institutionName;
    private String institutionLocation;
    private String longitude;
    private String latitude;
    private Date institutionDate;
    private String institutionDescription;
    private List<String> fileParam;
}
