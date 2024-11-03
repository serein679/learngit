package com.shxy.models.paramsBean.component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description id
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class editComponentParam {
    private Integer componentId;
    private Integer componentType;
    private String description;
}
