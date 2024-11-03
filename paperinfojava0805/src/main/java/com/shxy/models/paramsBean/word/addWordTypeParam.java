package com.shxy.models.paramsBean.word;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description id
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class addWordTypeParam {
    private String typeName;
    private String description;
}
