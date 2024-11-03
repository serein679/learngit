package com.shxy.models.paramsBean.institution;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;
import java.util.Date;

/**
 * @description
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class editInstitutionParam {
    private Integer institutionId;
    private String institutionName;
    private String institutionLocation;
    private String longitude;
    private String latitude;
    private Date institutionDate;
    private String institutionDescription;
}
