package com.shxy.models.paramsBean.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description 用户注册
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class registInfo {
    private String username;
    private String userpass;
    private Integer quesId;
    private String answer;
    private String picture;
}
