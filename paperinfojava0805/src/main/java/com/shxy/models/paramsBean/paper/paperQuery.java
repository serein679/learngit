package com.shxy.models.paramsBean.paper;

import com.shxy.models.paramsBean.BasicQueryParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description 纸张列表分页查询
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class paperQuery extends BasicQueryParam {
    private String paperName;
    private String paperDes;
    private Integer paperType;
    private String location;
    private String process;
    private String technology;
    private String creator;
    private Integer state;
}
