package com.shxy.models.paramsBean.word;

import com.shxy.models.paramsBean.BasicQueryParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description 敏感词类型列表分页查询
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class wordTypeQuery extends BasicQueryParam {
    private String typeName;
    private String description;
    private String creator;
    private Integer state;
}
