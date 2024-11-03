package com.shxy.models.views.operation;

import com.shxy.models.dbBean.feedback;
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
public class feedbackItem extends feedback implements Serializable {
    private String userName;
    private String objectName;
    private Integer objectState;
}
