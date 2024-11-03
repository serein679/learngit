package com.shxy.models.paramsBean.word;

import com.shxy.models.paramsBean.BasicQueryParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description 敏感词列表分页查询
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class wordQuery extends BasicQueryParam {
    private String wordName;
    private Integer type;
    private String creator;
    private Integer state;
}
