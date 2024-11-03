package com.shxy.models.paramsBean.personalCenter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description 修改密保和答案
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class quesAndAnswer {
    private Integer quesId;
    private String answer;
}
