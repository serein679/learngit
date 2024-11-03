package com.shxy.models.paramsBean.operation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description 投诉评论/回复
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class complainParam {
    private Integer id;
    private Integer type;
    private Integer contentType;
    private String content;
}
