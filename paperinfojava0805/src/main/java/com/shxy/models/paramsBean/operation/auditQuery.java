package com.shxy.models.paramsBean.operation;

import com.shxy.models.paramsBean.BasicQueryParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description 编辑内容
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class auditQuery extends BasicQueryParam {
    private Integer type;
    private Integer state;
}
