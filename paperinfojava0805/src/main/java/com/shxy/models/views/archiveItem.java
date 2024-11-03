package com.shxy.models.views;

import com.shxy.models.dbBean.archive;
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
public class archiveItem extends archive implements Serializable {
    private String institutionName;
    private String creator;
    private String pictureLocation;
}
