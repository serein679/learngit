package com.shxy.models.paramsBean.operation;

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
public class commentParam {
    private Integer id;
    private Integer type;
    private String content;
}
