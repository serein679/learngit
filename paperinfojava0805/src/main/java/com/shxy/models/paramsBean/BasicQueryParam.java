package com.shxy.models.paramsBean;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description 基础查询
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasicQueryParam {
    private Integer pageNum;
    private Integer pageSize;
    private String orderColumn;
    private Boolean isAsc;
}
