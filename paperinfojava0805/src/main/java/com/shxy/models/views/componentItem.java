package com.shxy.models.views;

import com.shxy.models.dbBean.component;
import com.shxy.models.paramsBean.BasicQueryParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description id
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class componentItem extends component implements Serializable {
    private String creator;
}
