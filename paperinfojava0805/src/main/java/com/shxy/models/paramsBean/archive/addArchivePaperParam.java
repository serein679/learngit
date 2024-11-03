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
public class addArchivePaperParam {
    private Integer paperId;
    private String ph;
    private String size;
    private String thickness;
    private String whiteness;
    private String color;
    private Integer hengWen;
    private String band;
    private Integer zongWen;
    private String zong;
    private Integer shuLiang;
    private Integer numbers;
    private Integer distribution;
    private String lianWen;
    private String beatDeg;
    private String degFiber;
}
