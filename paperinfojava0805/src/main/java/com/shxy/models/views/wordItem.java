package com.shxy.models.views;

import com.shxy.models.dbBean.fiber;
import com.shxy.models.dbBean.word;
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
public class wordItem extends word implements Serializable {
    private String creator;
    private String typeName;
}
