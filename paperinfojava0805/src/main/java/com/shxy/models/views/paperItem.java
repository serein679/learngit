package com.shxy.models.views;

import com.shxy.models.dbBean.fiber;
import com.shxy.models.dbBean.paper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class paperItem extends paper implements Serializable {
    private String creator;
    private String pictureLocation;
}
