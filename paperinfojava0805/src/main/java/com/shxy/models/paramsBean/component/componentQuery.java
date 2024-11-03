package com.shxy.models.paramsBean.component;

import com.shxy.models.paramsBean.BasicQueryParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description 成分列表分页查询
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class componentQuery extends BasicQueryParam {
    private String componentName;
    private Integer componentType;
    private String creator;
    private Integer state;
}
