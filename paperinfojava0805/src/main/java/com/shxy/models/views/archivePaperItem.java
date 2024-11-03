package com.shxy.models.views;

import com.shxy.models.dbBean.archivePaper;
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
public class archivePaperItem extends archivePaper implements Serializable {
    private String archiveName;
    private String paperName;
    private String creatorName;
    private Integer paperId;

}
