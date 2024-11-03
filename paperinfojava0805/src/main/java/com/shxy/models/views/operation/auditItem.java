package com.shxy.models.views.operation;

import com.shxy.models.dbBean.audit;
import com.shxy.models.dbBean.comment;
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
public class auditItem extends audit implements Serializable {
    private String userName;
    private String targetName;
}
