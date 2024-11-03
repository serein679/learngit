package com.shxy.models.paramsBean.archive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @description
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class addRecordParam {
    private String bianHao;
    private String time;
    private String author;
    private String language;
    private String classification;
    private String reference;
}
