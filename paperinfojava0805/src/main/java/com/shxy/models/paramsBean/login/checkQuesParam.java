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
public class checkQuesParam {
    private String username;
    private String answer;
}
