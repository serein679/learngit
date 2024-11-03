package com.shxy.models.views;

import com.shxy.models.dbBean.word;
import com.shxy.models.dbBean.wordType;
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
public class wordTypeItem extends wordType implements Serializable {
    private String creator;
    private Integer state;
}
