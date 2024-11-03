package com.shxy.models.paramsBean.user;

import com.shxy.models.paramsBean.BasicQueryParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description 用户列表分页查询
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class userQuery extends BasicQueryParam {
    private String userName;
    private Integer state;
}
