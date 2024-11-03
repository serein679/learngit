package com.shxy.models.views;

import com.shxy.models.dbBean.permission;
import com.shxy.models.dbBean.role;
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
public class roleItem extends role implements Serializable {
    private String creator;
}
