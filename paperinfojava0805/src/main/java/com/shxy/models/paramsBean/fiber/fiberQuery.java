package com.shxy.models.paramsBean.fiber;

import com.shxy.models.paramsBean.BasicQueryParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description 纤维列表分页查询
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class fiberQuery extends BasicQueryParam {
    private String fiberType;
    private String characteristic;
    private String creator;
    private Integer state;
}
