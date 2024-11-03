package com.shxy.models.paramsBean.user;

import com.shxy.models.paramsBean.BasicQueryParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description 角色列表分页查询
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class roleQuery extends BasicQueryParam {
    private String roleName;
    private String description;
    private String creator;
    private Integer state;
}
