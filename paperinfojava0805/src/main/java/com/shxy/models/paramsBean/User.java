package com.shxy.models.paramsBean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description 接收用户名密码便于进行jwt验证等
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String username;
    private String userpass;
}
