package com.shxy.models.paramsBean.personalCenter;

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
public class userFeedbackQuery extends BasicQueryParam {
    private Integer objectType;
    private String content;
    private Integer state;
}
