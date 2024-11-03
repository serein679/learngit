package com.shxy.models.views;

import com.shxy.models.dbBean.component;
import com.shxy.models.dbBean.fiber;
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
public class fiberItem extends fiber implements Serializable {
    private String creator;
}
