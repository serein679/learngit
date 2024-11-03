package com.shxy.models.views.operation;

import com.shxy.models.dbBean.role;
import com.shxy.models.dbBean.comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


/**
 * @description
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class commentItem extends comment implements Serializable {
    private String userName;
    private String picture;
    private String objectName;
    private Integer objectState;
}
