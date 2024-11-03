package com.shxy.models.paramsBean.archive;

import com.shxy.models.paramsBean.BasicQueryParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
/**
 * @description 档案列表分页查询
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class archiveQuery extends BasicQueryParam {
    private String archiveName;
    private String institutionName;
    private Integer archiveType;
    private String archiveContent;
    private String creator;
    private Integer state;
    private Date datefilter;
    private Date datefilter1;
}
