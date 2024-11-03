package com.shxy.models.views;

import com.shxy.models.dbBean.archivePaper;
import com.shxy.models.dbBean.picture;
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
public class pictureItem extends picture implements Serializable {
    private String creatorName;
}
