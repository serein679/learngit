package com.shxy.models.paramsBean.user;

import com.shxy.models.paramsBean.BasicQueryParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description 权限列表分页查询
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class permissionQuery extends BasicQueryParam {
    private String permissionName;
    private String description;
    private String creator;
    private Integer state;
}
