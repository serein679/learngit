package com.shxy.models.views.operation;

import com.shxy.models.dbBean.comment;
//import com.shxy.models.dbBean.reportComment;
import com.shxy.models.views.reportComment;
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
public class complainItem extends reportComment implements Serializable {
    private String userName;
    private String objectContent;
    private Integer objectState;
}
