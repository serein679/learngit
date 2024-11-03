package com.shxy.models.views;

import com.shxy.models.dbBean.fiber;
import com.shxy.models.dbBean.permission;
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
public class permissionItem extends permission implements Serializable {
    private String creator;
}
