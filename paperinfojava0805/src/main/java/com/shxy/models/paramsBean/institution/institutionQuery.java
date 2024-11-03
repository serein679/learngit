package com.shxy.models.paramsBean.institution;

import com.shxy.models.paramsBean.BasicQueryParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @description 敏感词列表分页查询
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class institutionQuery extends BasicQueryParam {
    private String institutionName;
    private String institutionLocation;
    private Date startDate;
    private Date endDate;
    private String institutionDescription;
    private String creator;
    private Integer state;
}
